package com.example.puzzlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.exit;

public class MainActivity3 extends AppCompatActivity {
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;
    private static String[] tileList;

    private static GestureDetectGridView3 mGridView;
    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();

        init();
        scramble();
        setDimensions();

    }

    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;
                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());

            }
        });
    }
    private int getStatusBarHeight(Context context){
        int result = 0;
        int resourceID = context.getResources().getIdentifier("status_bar_height","dimen","android");

        if(resourceID > 0){
            result = context.getResources().getDimensionPixelSize(resourceID);

        }
        return result;

    }
    private static void display(Context context) {
        ArrayList<Button>buttons =  new ArrayList<>();
        Button button;
        for(int i = 0; i < tileList.length; i++){
            button = new Button(context);

            if(tileList[i].equals("0")){
                button.setBackgroundResource(R.drawable.ima1);
            }else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.ima2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.ima3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.ima4);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.ima5);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.ima6);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.ima7);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.ima8);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.ima9);

            buttons.add(button);

        }
        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1;i > 0; i--){

            index = random.nextInt(i+1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;

        }
    }

    private void init(){
        mGridView = (GestureDetectGridView3) findViewById(R.id.grid3);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for(int i = 0;i < DIMENSIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

    private static void swap(Context context,int position, int swap){
        String newPosition = tileList[position + swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;
        display(context);
        if( isSolved()){
            Toast.makeText(context,"You Win!", Toast.LENGTH_SHORT).show();
        }

    }

    private static boolean isSolved() {
        boolean solved=false;

        for(int i = 0; i < tileList.length;i++){
            if(tileList[i].equals(String.valueOf(i))){
                solved = true;
            }else{
                solved = false;
                break;
            }
        }
        return solved;
    }

    public static void moveTiles(Context context, String direction, int position){

        //UPPER LEFT CORNER TILES
        if(position == 0){
            if(direction.equals(RIGHT))
                swap(context,position, 1);
            else if(direction.equals(DOWN))
                swap(context,position, COLUMNS);
            else
                Toast.makeText(context,"Inalid Move",Toast.LENGTH_SHORT).show();

        } //UPPER CENTRE TILES
        else if(position > 0 && position < COLUMNS - 1){
            if(direction.equals(LEFT))
                swap(context,position, -1);
            else if(direction.equals(DOWN))
                swap(context,position, COLUMNS);
            else if(direction.equals(RIGHT))
                swap(context,position, 1);
            else
                Toast.makeText(context,"Inalid Move",Toast.LENGTH_SHORT).show();
        }//UPPER RIGHT CORNER TILES
        else if(position == COLUMNS - 1){
            if(direction.equals(LEFT)) swap(context,position, -1);
            else if(direction.equals(DOWN)) swap(context,position, COLUMNS);
            else Toast.makeText(context,"Inalid Move",Toast.LENGTH_SHORT).show();
        }//LEFT SIDE TILES
        else if(position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS && position % COLUMNS == 0){
            if(direction.equals(UP)) swap(context,position, -COLUMNS);
            else if(direction.equals(RIGHT)) swap(context,position, 1);
            else if(direction.equals(DOWN)) swap(context,position, COLUMNS);
            else Toast.makeText(context,"Inalid Move",Toast.LENGTH_SHORT).show();
        }//RIGHT SIDE AND BOTTOM RIGHT CORNER TILES
        else if(position == COLUMNS * 2 - 1 || position == COLUMNS* 3 - 1){
            if(direction.equals(UP)) swap(context,position, -COLUMNS);
            else if(direction.equals(LEFT)) swap(context,position, -1);
            else if(direction.equals(DOWN)){
                if(position <= DIMENSIONS - COLUMNS -1) swap(context,position,COLUMNS);
                else Toast.makeText(context,"Invalid Move",Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(context,"Inalid Move",Toast.LENGTH_SHORT).show();
        }//BOTTOM LEFT CORNER TILES
        else if(position == DIMENSIONS - COLUMNS){
            if(direction.equals(UP)) swap(context,position,-COLUMNS);
            else if(direction.equals(RIGHT)) swap(context,position,1);
            else Toast.makeText(context,"Invalid Move",Toast.LENGTH_SHORT).show();
        }//BOTTOM CENTRE TILES
        else if(position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS){

            if(direction.equals(UP)) swap(context,position,-COLUMNS);
            else if(direction.equals(LEFT)) swap(context,position,-1);
            else if(direction.equals(RIGHT)) swap(context,position,1);
            else Toast.makeText(context,"Invalid Move",Toast.LENGTH_SHORT).show();
        }//CENTRE TILES
        else{
            if(direction.equals(UP)) swap(context,position,-COLUMNS);
            else if(direction.equals(LEFT)) swap(context,position,-1);
            else if(direction.equals(RIGHT)) swap(context,position,1);
            else swap(context,position,COLUMNS);
        }

    }


    public void backButton(View view) {
        exit(0);
    }
}

