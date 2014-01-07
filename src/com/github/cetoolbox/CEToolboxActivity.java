/*******************************************************************************
 * Copyright (C) 2012-2013 CNRS and University of Strasbourg
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
import com.github.cetoolbox.fragments.tabs.ExpertActivity;
import com.github.cetoolbox.fragments.tabs.SimpleActivity;
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
			intent = new Intent(this.getBaseContext(), SimpleActivity.class);
			/*
			 * intent.setClassName("proteomics.mobile.workbench",
			 * "CEToolboxSimpleActivity");
			 */
			/* .setClass(this, CEToolboxSimpleActivity.class); */
			spec = tabHost.newTabSpec("simple");
			spec.setContent(intent);
			spec.setIndicator("Injection");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), ExpertActivity.class);
			spec = tabHost.newTabSpec("expert");
			spec.setContent(intent);
			spec.setIndicator("Viscosity");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), AboutActivity.class);
			spec = tabHost.newTabSpec("conductivity");
			spec.setContent(intent);
			spec.setIndicator("Conductivity");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), AboutActivity.class);
			spec = tabHost.newTabSpec("flow");
			spec.setContent(intent);
			spec.setIndicator("Flow");
			tabHost.addTab(spec);

			intent = new Intent(this.getBaseContext(), AboutActivity.class);
			spec = tabHost.newTabSpec("About");
			spec.setContent(intent);
			spec.setIndicator("About");
			tabHost.addTab(spec);

			/* width = number of letters * 10 + 10 */
			int displayWidth = getWindowManager().getDefaultDisplay()
					.getWidth();
			int defaultTabWidth = 100;
			/* defaultTabWidth = tabHost.get */
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
