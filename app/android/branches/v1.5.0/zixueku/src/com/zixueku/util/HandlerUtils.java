package com.zixueku.util;

import android.os.Message;

import com.zixueku.base.BaseActivity;
import com.zixueku.base.BaseFragment;

public class HandlerUtils {
		
		private static Message getMessage(int what,Object obj){
			Message msg = Message.obtain();
			msg.what = what;
			msg.obj = obj;
			return msg;
		}
		
		private static Message getMessage(int what){
			Message msg = Message.obtain();
			msg.what = what;
			return msg;
		}
		
		public static void sendMessage(BaseActivity act,int what,Object obj){
			act.mHandler.sendMessage(getMessage(what, obj));
		} 
		
		public static void sendMessage(BaseActivity act,int what){
			act.mHandler.sendMessage(getMessage(what));
		}
		
		public static void sendMessage(BaseFragment act,int what,Object obj){
			act.mHandler.sendMessage(getMessage(what, obj));
		} 
		
		public static void sendMessage(BaseFragment act,int what){
			act.mHandler.sendMessage(getMessage(what));
		}

}
