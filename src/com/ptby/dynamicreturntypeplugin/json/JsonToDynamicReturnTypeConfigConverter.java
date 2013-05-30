package com.ptby.dynamicreturntypeplugin.json;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ptby.dynamicreturntypeplugin.ClassMethodConfig;
import com.ptby.dynamicreturntypeplugin.DynamicReturnTypeConfig;
import com.ptby.dynamicreturntypeplugin.FunctionCallConfig;

import java.util.ArrayList;
import java.util.List;

public class JsonToDynamicReturnTypeConfigConverter {

    public DynamicReturnTypeConfig convertJson( String json ) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson( json, JsonElement.class );
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray methodCalls = jsonObject.getAsJsonArray( "methodCalls" );
        List<ClassMethodConfig> classMethodConfigs = castJsonMethodCallConfigToClassMethodConfigs( methodCalls );

        JsonElement functionCalls = jsonObject.get( "functionCalls" );
        List<FunctionCallConfig> functionCallConfigs = castJsonMethodCallConfigToFunctionCallConfigs( functionCalls );

        return new DynamicReturnTypeConfig( classMethodConfigs, functionCallConfigs );
    }


    private List<ClassMethodConfig> castJsonMethodCallConfigToClassMethodConfigs( JsonElement methodCalls ) {
        ArrayList<ClassMethodConfig> classMethodConfigs = new ArrayList<ClassMethodConfig>();
        if ( methodCalls == null ) {
            return classMethodConfigs;
        }

        JsonArray jsonMethodConfigList = methodCalls.getAsJsonArray();
        for ( JsonElement jsonElement : jsonMethodConfigList ) {
            if ( !jsonElement.isJsonNull() ) {
                JsonObject jsonMethodCall = jsonElement.getAsJsonObject();
                ClassMethodConfig classMethodConfig = new ClassMethodConfig(
                        jsonMethodCall.get( "class" ).getAsString(),
                        jsonMethodCall.get( "method" ).getAsString(),
                        jsonMethodCall.get( "position" ).getAsInt()
                );

                classMethodConfigs.add( classMethodConfig );
            }
        }

        return classMethodConfigs;
    }


    private List<FunctionCallConfig> castJsonMethodCallConfigToFunctionCallConfigs( JsonElement functionCalls ) {
        ArrayList<FunctionCallConfig> functionCallConfigs = new ArrayList<FunctionCallConfig>();
        if ( functionCalls == null ) {
            return functionCallConfigs;
        }


        JsonArray jsonFunctionCalConfigList = functionCalls.getAsJsonArray();
        for ( JsonElement jsonElement : jsonFunctionCalConfigList ) {
            if ( !jsonElement.isJsonNull() ) {
                JsonObject jsonFunctionCall = jsonElement.getAsJsonObject();
                FunctionCallConfig classMethodConfig = new FunctionCallConfig(
                        jsonFunctionCall.get( "function" ).getAsString(),
                        jsonFunctionCall.get( "position" ).getAsInt()
                );

                functionCallConfigs.add( classMethodConfig );
            }
        }

        return functionCallConfigs;
    }
}