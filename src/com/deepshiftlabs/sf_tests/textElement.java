package com.deepshiftlabs.sf_tests;

public class textElement extends genericTextElement{
	private boolean isUnique=false;
	private boolean isUniqueCaseSensitive=false;
	
    textElement(String a_elementName, String a_elementSfId, String a_parentObjectType, boolean a_isRequired, int a_maxLength){
        super(a_elementName, a_elementSfId,a_parentObjectType, a_isRequired, a_maxLength);
        
        values.add(new checkValue("test <>\" test", constants.IT_IS_VALID_VALUE));
    }

    public void setUnique (boolean isCaseSensitive){
    	isUnique = true;
    	isUniqueCaseSensitive = isCaseSensitive;
    }
}