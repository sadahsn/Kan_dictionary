package com.kan.dic.client.ui;

import com.google.gwt.user.client.ui.Label;

public class MeaningLabelWidget extends Label {

	private static MeaningLabelWidget instance;
	public MeaningLabelWidget() {
		super();
	}
	
	public static MeaningLabelWidget get(){
		if(instance == null) instance= new MeaningLabelWidget();
		return  instance;
	}
}
