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
package com.github.cetoolbox.fragments.tabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Locale;
import com.github.cetoolbox.CEToolboxActivity;
import com.github.cetoolbox.CapillaryElectrophoresis;
import com.github.cetoolbox.R;

public class ExpertActivity extends Activity implements
		AdapterView.OnItemSelectedListener, View.OnClickListener {

	public static final String PREFS_NAME = "capillary.electrophoresis.toolbox.PREFERENCE_FILE_KEY";

	Button calculate;
	Button reset;
	EditText capillaryLengthValue;
	EditText diameterValue;
	EditText pressureValue;
	EditText durationValue;
	EditText viscosityValue;
	EditText toWindowLengthValue;
	EditText concentrationValue;
	EditText molecularWeightValue;
	EditText voltageValue;
	TextView tvConcentrationUnits;
	Spinner concentrationSpin;
	Spinner pressureSpin;
	String concentrationUnit;
	String pressureUnit;
	CapillaryElectrophoresis capillary;

	Double diameter;
	Double duration;
	Double viscosity;
	Double capillaryLength;
	Double pressure;
	Double toWindowLength;
	Double concentration;
	Double molecularWeight;
	Double voltage;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String languageToLoad = "en";
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());
		this.setContentView(R.layout.expert);

		capillaryLengthValue = (EditText) findViewById(R.id.capillaryLengthValue);
		diameterValue = (EditText) findViewById(R.id.diameterValue);
		toWindowLengthValue = (EditText) findViewById(R.id.toWindowLengthValue);
		pressureValue = (EditText) findViewById(R.id.pressureValue);
		durationValue = (EditText) findViewById(R.id.durationValue);
		viscosityValue = (EditText) findViewById(R.id.viscosityValue);
		concentrationValue = (EditText) findViewById(R.id.concentrationValue);
		molecularWeightValue = (EditText) findViewById(R.id.molecularWeightValue);
		voltageValue = (EditText) findViewById(R.id.voltageValue);
		concentrationSpin = (Spinner) findViewById(R.id.concentrationSpin);
		concentrationSpin.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> concentrationUnitsAdapter = ArrayAdapter
				.createFromResource(this, R.array.concentrationUnitArray,
						android.R.layout.simple_spinner_item);
		concentrationUnitsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		concentrationSpin.setAdapter(concentrationUnitsAdapter);

		pressureSpin = (Spinner) findViewById(R.id.pressureSpin);
		pressureSpin.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> pressureUnitsAdapter = ArrayAdapter
				.createFromResource(this, R.array.pressureUnitArray,
						android.R.layout.simple_spinner_item);
		pressureUnitsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pressureSpin.setAdapter(pressureUnitsAdapter);

		calculate = (Button) findViewById(R.id.button1);
		calculate.setOnClickListener(this);
		reset = (Button) findViewById(R.id.button2);
		reset.setOnClickListener(this);

		/* Restore preferences */
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		diameter = Double.longBitsToDouble(settings.getLong("diameter",
				Double.doubleToLongBits(30.0)));
		duration = Double.longBitsToDouble(settings.getLong("duration",
				Double.doubleToLongBits(21.0)));
		viscosity = Double.longBitsToDouble(settings.getLong("viscosity",
				Double.doubleToLongBits(1.0)));
		capillaryLength = Double.longBitsToDouble(settings.getLong(
				"capillaryLength", Double.doubleToLongBits(100.0)));
		pressure = Double.longBitsToDouble(settings.getLong("pressure",
				Double.doubleToLongBits(27.5792)));
		toWindowLength = Double.longBitsToDouble(settings.getLong(
				"toWindowLength", Double.doubleToLongBits(90.0)));
		concentration = Double.longBitsToDouble(settings.getLong(
				"concentration", Double.doubleToLongBits(21.0)));
		molecularWeight = Double.longBitsToDouble(settings.getLong(
				"molecularWeight", Double.doubleToLongBits(150000.0)));
		voltage = Double.longBitsToDouble(settings.getLong("voltage",
				Double.doubleToLongBits(30.0)));

		/* Set GlobalState values */
		CEToolboxActivity.fragmentData.setDiameter(diameter);
		CEToolboxActivity.fragmentData.setDuration(duration);
		CEToolboxActivity.fragmentData.setViscosity(viscosity);
		CEToolboxActivity.fragmentData.setCapillaryLength(capillaryLength);
		CEToolboxActivity.fragmentData.setPressure(pressure);
		CEToolboxActivity.fragmentData.setToWindowLength(toWindowLength);
		CEToolboxActivity.fragmentData.setConcentration(concentration);
		CEToolboxActivity.fragmentData.setMolecularWeight(molecularWeight);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		diameter = savedInstanceState.getDouble("diameter");
		duration = savedInstanceState.getDouble("duration");
		viscosity = savedInstanceState.getDouble("viscosity");
		capillaryLength = savedInstanceState.getDouble("capillaryLength");
		pressure = savedInstanceState.getDouble("pressure");
		toWindowLength = savedInstanceState.getDouble("toWindowLength");
		concentration = savedInstanceState.getDouble("concentration");
		molecularWeight = savedInstanceState.getDouble("molecularWeight");
		voltage = savedInstanceState.getDouble("voltage");

		/* Set GlobalState values */
		CEToolboxActivity.fragmentData.setDiameter(diameter);
		CEToolboxActivity.fragmentData.setDuration(duration);
		CEToolboxActivity.fragmentData.setViscosity(viscosity);
		CEToolboxActivity.fragmentData.setCapillaryLength(capillaryLength);
		CEToolboxActivity.fragmentData.setPressure(pressure);
		CEToolboxActivity.fragmentData.setToWindowLength(toWindowLength);
		CEToolboxActivity.fragmentData.setConcentration(concentration);
		CEToolboxActivity.fragmentData.setMolecularWeight(molecularWeight);
	}

	@Override
	public void onResume() {
		super.onResume();

		diameter = CEToolboxActivity.fragmentData.getDiameter();
		duration = CEToolboxActivity.fragmentData.getDuration();
		viscosity = CEToolboxActivity.fragmentData.getViscosity();
		capillaryLength = CEToolboxActivity.fragmentData.getCapillaryLength();
		pressure = CEToolboxActivity.fragmentData.getPressure();
		toWindowLength = CEToolboxActivity.fragmentData.getToWindowLength();
		concentration = CEToolboxActivity.fragmentData.getConcentration();
		molecularWeight = CEToolboxActivity.fragmentData.getMolecularWeight();

		/* Initialize content */
		editTextInitialize();
	}

	private void editTextInitialize() {
		capillaryLengthValue.setText(capillaryLength.toString());
		diameterValue.setText(diameter.toString());
		toWindowLengthValue.setText(toWindowLength.toString());
		pressureValue.setText(pressure.toString());
		durationValue.setText(duration.toString());
		viscosityValue.setText(viscosity.toString());
		concentrationValue.setText(concentration.toString());
		molecularWeightValue.setText(molecularWeight.toString());
		voltageValue.setText(voltage.toString());
	}

	@Override
	public void onClick(View view) {
		if (view == calculate) {
			boolean isFull = false;
			/* Parameter validation */
			diameter = Double.valueOf(diameterValue.getText().toString());
			duration = Double.valueOf(durationValue.getText().toString());
			viscosity = Double.valueOf(viscosityValue.getText().toString());
			capillaryLength = Double.valueOf(capillaryLengthValue.getText()
					.toString());
			if (pressureUnit.compareTo("psi") == 0) {
				pressure = Double.valueOf(pressureValue.getText().toString()) * 6894.8 / 100;
				;
			} else {
				pressure = Double.valueOf(pressureValue.getText().toString());
			}
			toWindowLength = Double.valueOf(toWindowLengthValue.getText()
					.toString());
			concentration = Double.valueOf(concentrationValue.getText()
					.toString());
			molecularWeight = Double.valueOf(molecularWeightValue.getText()
					.toString());
			voltage = Double.valueOf(voltageValue.getText().toString());

			/* If all is fine, save the data and compute */
			SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = preferences.edit();

			editor.putLong("diameter", Double.doubleToLongBits(diameter));
			editor.putLong("duration", Double.doubleToLongBits(duration));
			editor.putLong("viscosity", Double.doubleToLongBits(viscosity));
			editor.putLong("capillaryLength",
					Double.doubleToLongBits(capillaryLength));
			editor.putLong("pressure", Double.doubleToLongBits(pressure));
			editor.putLong("toWindowLength",
					Double.doubleToLongBits(toWindowLength));
			editor.putLong("concentration",
					Double.doubleToLongBits(concentration));
			editor.putLong("molecularWeight",
					Double.doubleToLongBits(molecularWeight));
			editor.putLong("voltage", Double.doubleToLongBits(voltage));
			editor.commit();

			capillary = new CapillaryElectrophoresis(pressure, diameter,
					duration, viscosity, capillaryLength, toWindowLength,
					concentration, molecularWeight);
			DecimalFormat myFormatter = new DecimalFormat("#.##");
			Double deliveredVolume = capillary.getDeliveredVolume(); /* nl */
			Double capillaryVolume = capillary.getCapillaryVolume(); /* nl */
			if (deliveredVolume > capillaryVolume) {
				deliveredVolume = capillaryVolume;
				isFull = true;
			}
			Double capillaryToWindowVolume = capillary.getToWindowVolume(); /* nl */
			Double injectionPlugLength = capillary.getInjectionPlugLength(); /* mm */
			Double timeToReplaceVolume = capillary.getTimeToReplaceVolume(); /* s */
			/* Compute injected quantity of analyte */
			Double analyteMass; /* ng */
			Double analyteMol; /* mmol */
			if (concentrationUnit.compareTo("g/L") == 0) {
				analyteMass = deliveredVolume * concentration;
				analyteMol = analyteMass / molecularWeight * 1000;
			} else {
				analyteMol = deliveredVolume * concentration;
				analyteMass = analyteMol * molecularWeight / 1000;
			}

			Double plugLength = deliveredVolume / capillaryVolume * 100;
			Double plugLengthToWindow = deliveredVolume
					/ capillaryToWindowVolume * 100;

			LayoutInflater li = LayoutInflater.from(this);
			View expertDetailsView = li.inflate(R.layout.expertresults, null);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setView(expertDetailsView);

			TextView title = new TextView(this);
			title.setText("Injection Details");
			title.setTextSize(20);
			title.setBackgroundColor(Color.DKGRAY);
			title.setTextColor(Color.WHITE);
			title.setPadding(10, 10, 10, 10);
			title.setGravity(Gravity.CENTER);
			builder.setCustomTitle(title);

			TextView tvHydrodynamicInjection = (TextView) expertDetailsView
					.findViewById(R.id.hydrodynamicInjectionValue);
			tvHydrodynamicInjection.setText(myFormatter.format(deliveredVolume)
					+ " nl");

			TextView tvCapillaryVolume = (TextView) expertDetailsView
					.findViewById(R.id.capillaryVolumeValue);
			tvCapillaryVolume.setText(myFormatter.format(capillaryVolume)
					+ " nl");

			TextView tvCapillaryToWindowVolume = (TextView) expertDetailsView
					.findViewById(R.id.capillaryToWindowVolumeValue);
			tvCapillaryToWindowVolume.setText(myFormatter
					.format(capillaryToWindowVolume) + " nl");

			TextView tvInjectionPlugLength = (TextView) expertDetailsView
					.findViewById(R.id.injectionPlugLengthValue);
			tvInjectionPlugLength.setText(myFormatter
					.format(injectionPlugLength) + " mm");

			TextView tvPlugLength = (TextView) expertDetailsView
					.findViewById(R.id.plugLengthValue);
			tvPlugLength.setText(myFormatter.format(plugLength));

			TextView tvPlugLengthToWindow = (TextView) expertDetailsView
					.findViewById(R.id.plugLengthToWindowValue);
			tvPlugLengthToWindow
					.setText(myFormatter.format(plugLengthToWindow));

			TextView tvTimeToReplaceVolume = (TextView) expertDetailsView
					.findViewById(R.id.timeToReplaceVolumeValue);
			tvTimeToReplaceVolume.setText(myFormatter
					.format(timeToReplaceVolume)
					+ " s\n"
					+ myFormatter.format(timeToReplaceVolume / 60) + " min");

			TextView tvInjectedAnalyte = (TextView) expertDetailsView
					.findViewById(R.id.injectedAnalyteValue);
			tvInjectedAnalyte.setText(myFormatter.format(analyteMass) + " ng\n"
					+ myFormatter.format(analyteMol) + " pmol");

			TextView tvInjectionPressure = (TextView) expertDetailsView
					.findViewById(R.id.injectionPressureValue);
			tvInjectionPressure.setText(myFormatter.format(pressure * duration
					* 100 / 6894.8)
					+ " psi.s");

			TextView tvFlowRate = (TextView) expertDetailsView
					.findViewById(R.id.flowRateValue);
			tvFlowRate.setText(myFormatter.format(capillaryVolume * 60
					/ timeToReplaceVolume)
					+ " nL/min");

			TextView tvFieldStrength = (TextView) expertDetailsView
					.findViewById(R.id.fieldStrengthValue);
			tvFieldStrength.setText(myFormatter
					.format(30 * 1000 / capillaryLength) + " V/cm");

			if (isFull) {
				TextView tvMessage = (TextView) expertDetailsView
						.findViewById(R.id.expertMessage);
				tvMessage.setTextColor(Color.RED);
				tvMessage.setTypeface(null, Typeface.BOLD);
				tvMessage.setText("Warning: the capillary is full !");
			}
			builder.setNeutralButton("Close",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dlg, int sumthin) {
							// do nothing – it will close on its own
						}
					});

			builder.show();

		} else if (view == reset) {
			editTextInitialize();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		if (parent == concentrationSpin) {
			concentrationUnit = (String) concentrationSpin
					.getItemAtPosition(position);
		} else if (parent == pressureSpin) {
			pressureUnit = (String) pressureSpin.getItemAtPosition(position);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		/* concentrationSpin.setText(""); */
	}

	@Override
	public void onPause() {
		CEToolboxActivity.fragmentData.setDiameter(Double.valueOf(diameterValue
				.getText().toString()));
		CEToolboxActivity.fragmentData.setDuration(Double.valueOf(durationValue
				.getText().toString()));
		CEToolboxActivity.fragmentData.setViscosity(Double
				.valueOf(viscosityValue.getText().toString()));
		CEToolboxActivity.fragmentData.setCapillaryLength(Double
				.valueOf(capillaryLengthValue.getText().toString()));
		CEToolboxActivity.fragmentData.setPressure(Double.valueOf(pressureValue
				.getText().toString()));
		CEToolboxActivity.fragmentData.setToWindowLength(Double
				.valueOf(toWindowLengthValue.getText().toString()));
		CEToolboxActivity.fragmentData.setConcentration(Double
				.valueOf(concentrationValue.getText().toString()));
		CEToolboxActivity.fragmentData.setMolecularWeight(Double
				.valueOf(molecularWeightValue.getText().toString()));

		super.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle state) {
		state.putDouble("diameter",
				Double.valueOf(diameterValue.getText().toString()));
		state.putDouble("duration",
				Double.valueOf(durationValue.getText().toString()));
		state.putDouble("viscosity",
				Double.valueOf(viscosityValue.getText().toString()));
		state.putDouble("capillaryLength",
				Double.valueOf(capillaryLengthValue.getText().toString()));
		state.putDouble("pressure",
				Double.valueOf(pressureValue.getText().toString()));
		state.putDouble("toWindowLength",
				Double.valueOf(toWindowLengthValue.getText().toString()));
		state.putDouble("concentration",
				Double.valueOf(concentrationValue.getText().toString()));
		state.putDouble("molecularWeight",
				Double.valueOf(molecularWeightValue.getText().toString()));
		state.putDouble("voltage",
				Double.valueOf(voltageValue.getText().toString()));

		super.onSaveInstanceState(state);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
