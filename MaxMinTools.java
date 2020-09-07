import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.ComponentCategory;

import com.google.appinventor.components.runtime.util.YailList;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.math.BigDecimal;

@DesignerComponent(version = 2,  description = "This Extension is created to get maximum/minimum value of a list. <br> Craeted by WatermelonIce <br><br> <b>My Profile in Kodular Community: </b><a href=\"https://community.kodular.io/u/WatermelonIce/summary\">My Profile</a><br><b>Support me by subscribing my YouTube Channel: </b> <a href=\"https://www.youtube.com/channel/UCBq9ouxr4C4OZG0sMCgmpwg\">WatermelonIce YouTube Channel</a>",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,   iconName = "https://lh5.googleusercontent.com/iUrGv0BDLUPZZeqrMcs3jLssBiYBpdWuOFBGAEMb4sxEi6TpyB7Akv1wgrB8KHXOUR7AhA3jo1upL3kvzuOt=w1920-h902")
@SimpleObject(external = true)
public class MaxMinTools extends AndroidNonvisibleComponent {
    private ComponentContainer container;
    public MaxMinTools(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }
  
    // Main Code Starts
  
  	@SimpleEvent(description = "Raises when error occured")
  	public void ErrorOccurred(String error, int errorCode) {
      EventDispatcher.dispatchEvent(this, "ErrorOccurred", error, errorCode);
    }
  
  	@SimpleFunction(description = "Get maximum value of a list")
    public String GetMaxValue(YailList list) {
      return get(list, 1);
    }
  
  
  	@SimpleFunction(description = "Get minimum value of a list")
    public String GetMinValue(YailList list) {
		return get(list, 0);
    }
  
  	@SimpleFunction(description = "Get maximum/minimum value")
  	public String GetValue(YailList list, int method) {
      return get(list, method);
    }
  
  	@SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Maximum method")
  	public int Maximum() {
      return 1;
    }
  
  	@SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Minimum method")
  	public int Minimum() {
      return 0;
    }
  
  	
  	//Private methods
  
  	private String get(YailList list, int num) {
      
      String error = null;
      String exceptionError = null;
      int errorCode = -1;
      String result = "";
      
      if (num != 0 && num != 1) {
        
        error = "Input method should either be 0 or 1";
        errorCode = 0;
          
      } else if (!(list instanceof YailList)) {
        
        error = "Input list should be a list";
        errorCode = 1;
        
      } else if (list.size() == 0) {
        
        error = "Input list cannot be empty";
        errorCode = 2;
        
	  } else {
      	try {
        
         List arrList = Arrays.asList(list.toArray());
         List<BigDecimal> arr = new ArrayList<BigDecimal>();
        
        for (Object value : arrList) {
          	BigDecimal x = new BigDecimal(value.toString());
         	 arr.add(x);
        	}
		
        	result = (num == 0 ? Collections.min(arr) : Collections.max(arr)).stripTrailingZeros().toPlainString();  
          
      	} catch (Exception e){
        
        exceptionError = e.getMessage();
          
      	} finally {
          
          	if (exceptionError != null) {
            	error = exceptionError;
        		errorCode = 3;
        	} else if (result.isEmpty()) {
        		error = "Unknown error, output is empty";
        		errorCode = 4;
        	}
        }
        
      }

      if (error != null) {
        ErrorOccurred(error, errorCode);
        result = "error";
      }
        
      return result;
        
    }
  
}