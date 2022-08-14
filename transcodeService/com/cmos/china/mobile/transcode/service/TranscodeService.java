package com.cmos.china.mobile.transcode.service;

import java.io.File;
import java.util.List;

import com.cmos.china.mobile.transcode.utlity.TranscodeUtlity;

public class TranscodeService {

	public static Boolean transcode(String inputPath , String outputPath, String outputName){
		File tfFile = new File(outputPath);
		if (!tfFile.exists()) {
			tfFile.mkdir();
		}
		Boolean superResult = TranscodeUtlity.videoTranscode(inputPath,outputPath,outputName,"super");
		if(!superResult){
			return false;
		}
		Boolean highResult = TranscodeUtlity.videoTranscode(inputPath,outputPath,outputName,"high");
		if(!highResult){
			return false;
		}
		Boolean standardResult = TranscodeUtlity.videoTranscode(inputPath,outputPath,outputName,"standard");
		if(!standardResult){
			return false;
		}
		Boolean fluencyResult = TranscodeUtlity.videoTranscode(inputPath,outputPath,outputName,"fluency");
		if(!fluencyResult){
			return false;
		}
        return true;
	}
	
	public static Boolean combine(String inputBase, List<String> inputPathList, String outputPath, String outputName){
		if(inputPathList.size() > 1){
			Boolean result = TranscodeUtlity.videoCombine(inputBase, inputPathList, outputPath, outputName);
			return result;
		}else{
			return false;
		}
	}
}
