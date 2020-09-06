package com.felix.vrphoto.objects;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.felix.vrphoto.utils.BitmapUtil;

import org.rajawali3d.Object3D;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.textures.ATexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.primitives.Plane;

public class VRPlane extends Object3D {

	public Plane plane;

	private Material material;

	public VRPlane(String label, int color, float height, float width) {

		plane = new Plane(width, height, 1, 1, 1);
		
		Bitmap bitmap = BitmapUtil.textAsString(label, 40, Color.WHITE,(int)height*10,(int)width*10);
		
		material = new Material();
		try {
			material.addTexture(new Texture("texture", bitmap));
		} catch (ATexture.TextureException e) {
			e.printStackTrace();
		}
		material.setColorInfluence(0.5f);
		material.setColor(color);
		
		plane.setMaterial(material);

		addChild(plane);
	}

}
