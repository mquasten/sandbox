package de.mq.profile.domain.rule;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

import de.mq.profile.domain.Feature.FeatureType;
import de.mq.profile.domain.FeatureValue;
import de.mq.profile.domain.FeatureValueRule;

@Rule()
public class MinMaxRangeRule implements FeatureValueRule {
	
private FeatureValue featureValue;

	
	MinMaxRangeRule(final FeatureValue featureValue) {
		this.featureValue = featureValue;
	}
	

	
	
	 
	 
	 @Condition()
	   public boolean when() {
		
		 	if( ! featureValue.canConvert() ) {
		 		return false;
		 	}
		 
		 	Number value = (Number) featureValue.getValue();
		 
		 	if( value == null){
		 		return true;
		 	}
		 	
		 	Number min = Double.MAX_VALUE * (-1);
	      if( featureValue.getFeature().getMinRange()!=null) {
	      	min=featureValue.getFeature().getMinRange();
	      }
	      Number max = Double.MAX_VALUE;
	      if( featureValue.getFeature().getMaxRange()!=null) {
	      	max=featureValue.getFeature().getMaxRange();
	      }
	      
	   
	      
	     if ( value.doubleValue() < min.doubleValue()) {
	   	  return true;
	     }
	     
	     if( value.doubleValue() > max.doubleValue()){
	   	  return true;
	     }
	     return false;
	      
	   }
	   
	  
		@Action
	   public void then()  {
		
			
			
			final String min  = featureValue.getFeature().getMinRange() == null ?"-&infin;": asString( featureValue.getFeature().getMinRange());
			final String max  = featureValue.getFeature().getMaxRange() == null ? "&infin;" : asString(featureValue.getFeature().getMaxRange());
			
	      featureValue.addError(String.format("invalid_range,%s,%s", min , max));
	   }
		
		private String asString(final Number value) {
			final DecimalFormat nf = new DecimalFormat();
			nf.setGroupingUsed(false);
			DecimalFormatSymbols decimalFormatSymbol =new DecimalFormatSymbols();
			decimalFormatSymbol.setDecimalSeparator('.');
			nf.setDecimalFormatSymbols(decimalFormatSymbol);
			if( featureValue.getFeature().getFeatureType()==FeatureType.Integer) {
				nf.setMinimumFractionDigits(0);
				return nf.format(value.longValue());
			}
			nf.setMaximumFractionDigits(4);
			return nf.format(value.doubleValue());
		}
		
		 @Priority
		   public int priortity() {
		   	return 2;
		   }
}
