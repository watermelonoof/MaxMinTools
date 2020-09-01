import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.common.ComponentCategory;

import com.google.appinventor.components.runtime.util.YailList;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.math.BigDecimal;

@DesignerComponent(version = 2,  description = "This Extension was created to get maximum/minimum value of a list. <br> Craeted by WatermelonIce <br><br> <b>My Profile in Kodular Community: </b><a href=\"https://community.kodular.io/u/WatermelonIce/summary\">My Profile</a><br><b>Support me by subscribing my YouTube Channel: </b> <a href=\"https://www.youtube.com/channel/UCBq9ouxr4C4OZG0sMCgmpwg\">WatermelonIce YouTube Channel</a>",
        category = ComponentCategory.EXTENSION,
        nonVisible = true,   iconName = "https://lh3.googleusercontent.com/fife/ABSRlIrbdVhN_DWUrikiuOM3CqaPhAnZzX2t0rkZ4f0VZ61TvUNjwRAVa4PfOxw44NOfDvipC7_YvARRTniZnHHbKC-1FImQhG7NFzpgJ26HB8E0VCIgBKgYFQKUAecg0m-Y92YazOw_p6nWmjxWEsEKbsLhAN3iXzG7YwABK3XydABEzBf_i4OcTdGChwzuHLFwqzmuWr1eRd-AlQteGOk2-GMcLuYksBgkxrkdpWy7qNd-JwPJ1EV_MISf65jHiZcGhlJ7jy4I837jqtP2ZNf1UM5hFJzunNMsgPFohH1JxZ-zmjlrY0oW-2e4jMTUIwaxvqTfId90pSjl8CjPPtuERHR5FUK556MnuYlEvUBIZe1m4Tl6aKjo9jT8gZxAS1-wewEMCdJtbaezPESs3dZylg1krNoBuYQ07-Hjgw29gTpIwxCz174M3iNJ_5UhpP0Qu3QRZWYbFb1NKAjU2a9ySsRLZQMdqFyUbxEdWUIYG06c3ApmbOcBWGsZcE7K2d_nVeXz_iMCUihNzV004J_gMNlH2MB4PT6xVlzfuNlDGMjihdZGlgFUS98qK1_LabDvziaR8RLHpKn02tJLxJYgaQ0akmKwC_ETVHrwe2WNelRYcJlVxoHWpCtS5jerQ-GAgM1buZP9paxpfN4lZcmXUJ8toKXS-jEFVKL8YCS52-fmtd-RcUimzf-LXN1O3FQQYSSS_kbeeenj9ctuOOnRwR6kAa_VtzvKjQ=w1920-h902-ft")
@SimpleObject(external = true)
public class MaxMinTools extends AndroidNonvisibleComponent {
    private ComponentContainer container;
    /**
     * @param container container, component will be placed in
     */
    public MaxMinTools(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }
  
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