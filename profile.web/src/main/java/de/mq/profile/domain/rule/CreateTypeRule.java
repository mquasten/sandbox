package de.mq.profile.domain.rule;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

import de.mq.profile.domain.Feature.FeatureType;
import de.mq.profile.domain.FeatureValue;
import de.mq.profile.domain.FeatureValueRule;


@Rule(name="CreateTypeRule")
public class CreateTypeRule implements FeatureValueRule {

	private FeatureValue featureValue;

	
	
	CreateTypeRule(final FeatureValue featureValue) {
		this.featureValue = featureValue;
	}



	@Condition()
   public boolean when() {
      final String value = featureValue.getValueAsString(); 
      
      if( value== null) {
      	return false;
      }
      
      
      if( featureValue.getFeature().getFeatureType().equals(FeatureType.String)) {
      	
      	return false;
      }
      
      
      
      
      if( value.trim().isEmpty()) {
      	return false;
      }
      
     
      return ! featureValue.canConvert();
      
   }
   
   
  
	@Action
   public void then()  {
      featureValue.addError(String.format("invalid_%s", featureValue.getFeature().getFeatureType().name().toLowerCase()));
      
   }


   @Priority
   public int priortity() {
   	return 1;
   }
	

	
	

}
