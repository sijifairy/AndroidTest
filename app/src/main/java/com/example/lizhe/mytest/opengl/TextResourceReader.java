package com.example.lizhe.mytest.opengl;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lizhe on 2016/12/20.
 */

public class TextResourceReader {

    public static String readTextFileFromResource(Context context, int resourceId) {
        StringBuilder body = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null) {
                body.append(nextLine).append('\n');
            }
        } catch (IOException e) {

        }

        return body.toString();
    }
}
