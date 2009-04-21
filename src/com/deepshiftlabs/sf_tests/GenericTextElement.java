package com.deepshiftlabs.sf_tests;

import com.thoughtworks.selenium.DefaultSelenium;

public class GenericTextElement extends GenericElement {
	
    public GenericTextElement(String a_elementName, String a_elementSfId, String a_parentObjectType, boolean a_isRequired, int a_maxLength){
        super(a_elementName, a_elementSfId,a_parentObjectType, a_isRequired);
        validValue = "a";
        setInputLength(a_maxLength);

// there we can't place symbols <> and "  because UrlElement can't display them 		

        values.add(new CheckValue("test", Constants.IT_IS_VALID_VALUE));
        values.add(new CheckValue("test!@#", Constants.IT_IS_VALID_VALUE));
        values.add(new CheckValue("test��ar", Constants.IT_IS_VALID_VALUE));
        values.add(new CheckValue("test...!+=%$*()!@#$%^&*?{}[]", Constants.IT_IS_VALID_VALUE));		
        values.add(new CheckValue("debug test to find if we corect work with lenghts of check values more than input maxsize (there are near 100 symbols here)", Constants.IT_IS_INVALID_VALUE));
    }    
    
    public void setInputLength (int a_length){
        inputLength =  a_length;
     }
     
     public int getInputLength (){
       return inputLength;
     }
     
    public boolean isValueValidForThisElementLength(CheckValue theValue){
    	if (theValue.value.length()>inputLength){
    		action.error("Length of check value _"+theValue.value+"_ is greater then element _"+elementName+"_ max size ("+inputLength+"), value skipped.");
    		return false;
    	}
    	return true;
    }     
     
     public int checkMaxLengthRunCount=0;
     public int checkMaxLength(DefaultSelenium selInstance){
      	if (checkMaxLengthRunCount>0) {
    		action.info("checkMaxLength for element _"+elementSfId+"_ already was performed, skipping");      		
      		return Constants.RET_SKIPPED;
      	}
      	checkMaxLengthRunCount++;    	 
    	 
         String testString;
         int realLength;
         char validChar;
         
         if (validValue.length()>inputLength){
        	 action.error("Can't perform check because max Length is less than validValue");
        	 return Constants.RET_ERROR;
         } 
         validChar = validValue.charAt(0);
         testString = validValue;
         
         while (testString.length() < (inputLength+1) ){
        	 testString = validChar+testString;
         }
         
         action.typeText(selInstance, writeLocator, testString);
         realLength = action.readValue(selInstance, writeLocator).length();
         if (realLength != inputLength){
             while (testString.length() < 256){
            	 testString = testString+testString;
             }
             action.typeText(selInstance, writeLocator, testString);
             realLength = action.readValue(selInstance, writeLocator).length();
        	 action.error ("Real maxLenght of  _"+ elementName + "_ is "+realLength+" (should be "+inputLength+" )");
        	 action.getScreenshot(selInstance, true);        	 
         }
        action.info ("Real maxLenght for _"+ elementName + "_ is OK.");
        action.getScreenshot(selInstance, false);        
        return Constants.RET_OK;
     }
    
     public int  checkAll (DefaultSelenium selInstance){
    	int returnedValue;

    	returnedValue = super.checkAll(selInstance);
    	if (returnedValue!=Constants.RET_OK)
    		return returnedValue;
    	
     	checkMaxLength(selInstance);     	
     	return Constants.RET_OK;
     }         
}