package com.jhd.framework.util;


public class AlphaHexadecimalUtil {

	public static final char[] ALPHA_SEQ = new char[] { '0',
		'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public static String increaseAlphaString(String alphaString){
		
		char[] alphaCharArray = alphaString.toCharArray();

		for(int i = alphaCharArray.length - 1; i >= 0; i--){
			AlphaResult ar = fetchNextChar(alphaCharArray[i]);
			alphaCharArray[i] = ar.getCurrentAlphaChar();
			if (!ar.isOver()){
				break;
			} 
		}
		String seqCode = String.valueOf(alphaCharArray);
		return seqCode;
	}
	

	public static String parseLong(long longValue, int alphaLen){
		//increaseAlphaString()
		String initAlpha = "000000000000000001";
		String startAlpha = initAlpha.substring(initAlpha.length() - alphaLen);
		for (long i = 1; i < longValue; i++){
			startAlpha = increaseAlphaString(startAlpha);
		}
		return startAlpha;
	}
	
	private static AlphaResult fetchNextChar(char alphaChar){
		 AlphaResult rst = new AlphaResult();
		
		for(int i = 0; i < ALPHA_SEQ.length; i++){
			if (alphaChar == ALPHA_SEQ[i]){
				if ((i + 1) >= ALPHA_SEQ.length){
					rst.setOver(true);
					rst.setCurrentAlphaChar(ALPHA_SEQ[0]);
					
				} else {
					rst.setCurrentAlphaChar(ALPHA_SEQ[i + 1]);
				}
				
				return rst;
			}
		}

		throw new RuntimeException();
	}
	
	static class AlphaResult{
		private boolean isOver;
		private char currentAlphaChar;

		public boolean isOver() {
			return isOver;
		}

		public void setOver(boolean isOver) {
			this.isOver = isOver;
		}

		public char getCurrentAlphaChar() {
			return currentAlphaChar;
		}

		public void setCurrentAlphaChar(char currentAlphaChar) {
			this.currentAlphaChar = currentAlphaChar;
		}
	}
	
	public static void main(String[] args) {

		System.out.println(parseLong(1334567L, 4));
	}
}
