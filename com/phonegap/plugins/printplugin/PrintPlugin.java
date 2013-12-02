package com.phonegap.plugins.printplugin;


import java.util.List;
import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.net.Uri;
import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;

//import com.phonegap.api.Plugin;
//import com.phonegap.api.PluginResult;

import org.json.JSONException;
import org.json.JSONObject;

public class PrintPlugin extends Plugin {
        private static final String ADR_PRINT   = "org.androidprinting.intent.action.PRINT";
        private static final String ADR_SHARE   = "com.dynamixsoftware.printershare";
        //private static final String ADR_CLOUD   = "com.sec.android.app.mobileprint.PRINT";
       
       
        @Override
        public PluginResult execute(String action, JSONArray args, String callbackId) {
                Context ctx2                                    = this.cordova.getActivity().getApplicationContext();
                try {
                        JSONObject jo   = args.getJSONObject(0);
                        Uri uri                 = Uri.parse(jo.getString("fileURI"));
                        doPrintIntent(uri, jo.getString("mimeType"), ctx2);
                        //Log.e("org.opencorebanking", "get a intent action");
                        return new PluginResult(PluginResult.Status.OK);
                } catch (JSONException e) {
                        //Log.e("org.opencorebanking", "Error in Intent action");
                        return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
                }
        }
       
        /*@SuppressWarnings("deprecation")*/
        private void doPrintIntent(Uri fileURI, String mimeType, Context ctx) {
               
        	
        	
 
                if( ctx.getPackageManager().getLaunchIntentForPackage(ADR_SHARE) != null ){
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setPackage("com.dynamixsoftware.printershare");
                        i.setDataAndType(fileURI, mimeType);
                        this.cordova.startActivityForResult(this, i, 0);
                        //Log.e("org.opencorebanking", ADR_SHARE);             
 
                } else {
                	
                	Intent printIntent = new Intent(Intent.ACTION_SEND);
                	printIntent.setType("text/html");
                	printIntent.putExtra(Intent.EXTRA_TITLE, "some cool title for your document");
                	printIntent.putExtra(Intent.EXTRA_STREAM, fileURI);

                    this.cordova.startActivityForResult(this, printIntent, 0);
                    Log.e("org.opencorebanking", "No Intent available");
                	//startActivity(printIntent);
                	
                        /*if( this.isIntentAvailable(ADR_CLOUD, ctx) != false ){
                                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                                i.setPackage("com.pauloslf.cloudprint");
                                i.setDataAndType(fileURI, mimeType);
                                this.cordova.startActivityForResult(this, i, 0);
                                //Log.e("org.opencorebanking", ADR_CLOUD);
                        } else {*/
                               
                				/*
                				if( this.isIntentAvailable(ADR_PRINT, ctx) != false) {
                                        Intent sendIntent = new Intent(ADR_PRINT);
                                        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                        sendIntent.setDataAndType(fileURI, mimeType);
                                        this.cordova.startActivityForResult(this, sendIntent, 0);
                                        //Log.e("org.opencorebanking", ADR_PRINT);
                                } else {
                                        Intent sendIntent = new Intent(android.content.Intent.ACTION_VIEW);
                                        sendIntent.setDataAndType(fileURI, mimeType);
                                        this.cordova.startActivityForResult(this, sendIntent, 0);
                                        Log.e("org.opencorebanking", "No Intent available");
                                }
                                */
                                
                                
                        /*}*/
                }
        }
        //http://android-developers.blogspot.mx/2009/01/can-i-use-this-intent.html
        /**
         * Indicates whether the specified action can be used as an intent. This
         * method queries the package manager for installed packages that can
         * respond to an intent with the specified action. If no suitable package is
         * found, this method returns false.
         *
         * @param action The Intent action to check for availability.
         *
         * @return True if an Intent with the specified action can be sent and
         *         responded to, false otherwise.
         */
        private boolean isIntentAvailable(String action, Context ctxX) {
               
            final PackageManager packageManager = ctxX.getPackageManager();
 
            final Intent intent = new Intent(action);
            List<ResolveInfo> list =
                    packageManager.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
            return list.size() > 0;
        }
 
}