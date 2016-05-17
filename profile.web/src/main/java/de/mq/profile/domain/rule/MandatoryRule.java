package de.mq.profile.domain.rule;


import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

import de.mq.profile.domain.FeatureValue;
import de.mq.profile.domain.FeatureValueRule;


@Rule(name="MandatoryRule")
public class MandatoryRule implements FeatureValueRule{

	private  FeatureValue featureValue;
	

	
	MandatoryRule(FeatureValue featureValue) {
	
		this.featureValue = featureValue;
	}



	@Condition()
   public boolean when() {
      final String value = featureValue.getValueAsString(); 
      
      if( value== null) {
      	return true;
      }
      return value.trim().isEmpty();
      
   }
   
   
  
	@Action
   public void then()  {
      featureValue.addError("mandatory");
   }


   @Priority
   public int priortity() {
   	return 1;
   }
	
	

}
