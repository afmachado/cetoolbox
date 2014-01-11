/*******************************************************************************
 * Copyright (C) 2012-2014 CNRS and University of Strasbourg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.cetoolbox;

import com.github.cetoolbox.fragments.tabs.AboutActivity;
import com.github.cetoolbox.fragments.tabs.ConductivityActivity;
import com.github.cetoolbox.fragments.tabs.FlowrateActivity;
import com.github.cetoolbox.fragments.tabs.InjectionActivity;
import com.github.cetoolbox.fragments.tabs.MobilityActivity;
import com.github.cetoolbox.fragments.tabs.ViscosityActivity;
import com.github.cetoolbox.GlobalState;
import android.os.Bundle;
import android.app.TabActivity;
import android.widget.TabHost;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import com.github.cetoolbox.R;

public class CEToolboxActivity extends TabActivity {

	static public GlobalState fragmentData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		fragmentData = null;

		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Reusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab
		Resources res = getResources();
		try {
			intent = new Intent(this.getBaseContext(), InjectionActivity.class);
			spec = tabHost.newTabSpec("injection");
			spec.setContent(intent);
			spec.setIndicator("Injection");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(),
					ConductivityActivity.class);
			spec = tabHost.newTabSpec("Conductivity");
			spec.setContent(intent);
			spec.setIndicator("Conductivity");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), FlowrateActivity.class);
			spec = tabHost.newTabSpec("flowRate");
			spec.setContent(intent);
			spec.setIndicator("FlowRate");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), MobilityActivity.class);
			spec = tabHost.newTabSpec("mobility");
			spec.setContent(intent);
			spec.setIndicator("Mobility");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), ViscosityActivity.class);
			spec = tabHost.newTabSpec("viscosity");
			spec.setContent(intent);
			spec.setIndicator("Viscosity");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), AboutActivity.class);
			spec = tabHost.newTabSpec("about");
			spec.setContent(intent);
			spec.setIndicator("About");
			tabHost.addTab(spec);

			/* width = number of letters * 10 + 10 */
			int displayWidth = getWindowManager().getDefaultDisplay()
					.getWidth();
			int defaultTabWidth = 100;
			if ((defaultTabWidth * tabHost.getTabWidget().getChildCount()) < displayWidth) {
				defaultTabWidth = (int) Math.ceil(displayWidth
						/ tabHost.getTabWidget().getChildCount());

			}

			for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
				tabHost.getTabWidget().getChildAt(i).getLayoutParams().width = defaultTabWidth;
			}

			/*
			 * tabHost.getTabWidget().getChildAt(1).getLayoutParams().width =
			 * 70; tabHost.getTabWidget().getChildAt(2).getLayoutParams().width
			 * = 60;
			 * tabHost.getTabWidget().getChildAt(3).getLayoutParams().width =
			 * 160; tabHost.getTabWidget().getChildAt(4).getLayoutParams().width
			 * = 160;
			 */
			tabHost.setCurrentTab(0);
		} catch (ActivityNotFoundException e) {
			/* e.printStackTrace(); */
		}

	}
}
