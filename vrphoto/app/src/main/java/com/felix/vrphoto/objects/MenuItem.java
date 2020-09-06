package com.felix.vrphoto.objects;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.felix.vrphoto.Constants;
import com.felix.vrphoto.listeners.OnBlurListener;
import com.felix.vrphoto.listeners.OnSelectListener;
import com.felix.vrphoto.listeners.OnViewListener;
import com.felix.vrphoto.utils.BitmapUtil;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Plane;

public class MenuItem  extends Object3D implements Selectable{
	
	private Plane plane;

	private Material material;
	
	private Texture texture;
	
	private String label;
	
	private double Z_DISPLACE = 0f;
	
	private double z;
	
	private boolean isViewed;
	
	private boolean isDisabled;
	
	private String value;
	
	private OnSelectListener onSelectListener;
	
	private OnBlurListener onBlurListener;
	
	private OnViewListener onViewListener;
	
	private int bgColor = Color.RED;
	
	private Bitmap bitmap;
	
	public MenuItem(String label, double z, int bgColor) {
		
		this.z= z;
		this.label = label;
		this.setValue(label);
		this.bgColor = bgColor;


		plane = new Plane(10f,2f,1, 1, 1);

		bitmap = BitmapUtil.textAsString(label, 20, Color.WHITE,50,250);
		
		material = new Material();
		texture =new Texture("texture", bitmap);

		try {
			material.addTexture(texture);
		} catch (ATexture.TextureException e) {
			e.printStackTrace();
		}
		material.setColorInfluence(0.5f);
		material.setColor(bgColor);

		plane.setMaterial(material);

		addChild(plane);
		setZ(z);
	}

	@Override
	public void onView() {
		setZ(z+Z_DISPLACE);
		material.setColor(0xFFFFA500);
		isViewed = true;
		
		if(onViewListener!=null){
			Event e = new Event();
			onViewListener.onView(e);
		}
	}

	@Override
	public void onBlur() {
		setZ(z);
		if(isDisabled){
			material.setColor(Constants.NO_ACCESS_COLOR);
		}else{
			material.setColor(bgColor);
		}
		isViewed = false;
		if(onBlurListener!=null){
			Event e = new Event();
			onBlurListener.onBlur(e);
		}
	}

	@Override
	public void onSelect() {
		if(onSelectListener!=null){
			Event e = new Event();
			e.current = this;
			onSelectListener.onSelect(e);
		}
	}
	
	public float[] getModelView(){
		return plane.getModelViewMatrix().getFloatValues();
	}
	
	public float[] getModel(){
		return plane.getModelMatrix().getFloatValues();
	}
	
	public boolean isViewed() {
		return isViewed;
	}
	
	public String getLabel() {
		return label;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		this.onSelectListener = onSelectListener;
	}

	public void setOnBlurListener(OnBlurListener onBlurListener) {
		this.onBlurListener = onBlurListener;
	}

	public void setOnViewListener(OnViewListener onViewListener) {
		this.onViewListener = onViewListener;
	}

	@Override
	public boolean isDisabled() {
		return isDisabled;
	}

	@Override
	public void setDisabled(boolean isDisabled) {
		if(isDisabled==true){
			material.setColor(Constants.NO_ACCESS_COLOR);
		}else{
			material.setColor(bgColor);
		}
		this.isDisabled = isDisabled;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void release(){
		
		texture = null;
		material = null;
		plane = null;
		onSelectListener = null;
		onViewListener = null;
		onBlurListener = null;
		bitmap.recycle();
		bitmap = null;
	}
	
	public void finalize(){
		Log.e("DISTROY", this.label);
	}
	
	public void setLabel(String label) {
		this.label = label;
		bitmap = BitmapUtil.textAsString(label, 20, Color.WHITE,50,250,bitmap);
		
		material.removeTexture(texture);
		
		texture =new Texture("texture", bitmap);
		texture.setBitmap(bitmap);
		
		try {
			material.addTexture(texture);
		} catch (ATexture.TextureException e) {
			e.printStackTrace();
		}
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
		material.setColor(bgColor);
	}

}
