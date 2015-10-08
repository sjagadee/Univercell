/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/
package app.nichepro.activities.mcommerce;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PhotoContestActivity extends BaseDefaultActivity implements
		OnClickListener, ResponseParserListener {
	private WebRequestTask photoRequestTask;
	private ResponseMesssagType msgType;
	private ImageView captionImgView;
	private EditText captionTxt;
	private Button btnUpload;
	private Button btnPhotoFrame;
	private Button btnbSelectPhoto;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	private Bitmap mSelectedImage;
	private String mFilePath;
	private String fileExtension;
	public static final int MEDIA_TYPE_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photocontest);
		captionImgView = (ImageView) findViewById(R.id.captionImg);
		captionTxt = (EditText) findViewById(R.id.captionTxt);
		btnUpload = (Button) findViewById(R.id.bPhotoContest);
		btnbSelectPhoto = (Button) findViewById(R.id.bSelectPhoto);
		btnPhotoFrame = (Button) findViewById(R.id.bPhotoFrameIcon);
		btnbSelectPhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				launchPhotoPickerOption();
			}
		});

		btnUpload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uploadPhotoInContest();
			}
		});

	}

	void showImageAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information,
				"Select an image before uploading", R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	void showCaptionAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information,
				"Enter caption before uploading", R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	public void uploadPhotoInContest() {
		// File file = new File(mFilePath);
		if (mFilePath == null || mFilePath.length() > 0
				&& mSelectedImage == null) {
			showImageAlert();
		} else if (captionTxt.getText().toString().compareTo("") == 0) {
			showCaptionAlert();
		} else {
			photoRequestTask = new WebRequestTask(
					WebLinks.getLoginLink(WebLinks.PHOTO_CONTEST_CAPTION),
					this, this);

			if (fileExtension != null && fileExtension.length() > 0) {
				photoRequestTask.AddParam(Constants.PARAM_FILE_EXTENSION,
						fileExtension);
			} else
				photoRequestTask
						.AddParam(Constants.PARAM_FILE_EXTENSION, "jpg");

			photoRequestTask
					.AddParam(Constants.PARAM_DATA_FILE, mSelectedImage);
			photoRequestTask.AddParam(Constants.PARAM_CAPTION, captionTxt
					.getText().toString());
			photoRequestTask.execute();
		}

	}

	public void launchPhotoPickerOption() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Choose image picker");
		// Set an EditText view to get user input

		alert.setPositiveButton("Open Camera",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						openCamera();
					}
				});

		alert.setNegativeButton("Open Gallery",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Canceled.
						openGallery();
					}
				});

		alert.show();

	}

	public static int PIC_IMAGE_FROM_GALLERY = 1;

	public void openCamera() {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	private void openGallery() {
		Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, PIC_IMAGE_FROM_GALLERY);
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		}
		// else if(type == MEDIA_TYPE_VIDEO) {
		// mediaFile = new File(mediaStorageDir.getPath() + File.separator +
		// "VID_"+ timeStamp + ".mp4");
		// }
		else {
			return null;
		}

		return mediaFile;
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
				&& resultCode == RESULT_OK) {
			String filePath = fileUri.getPath();
			try {
				hasValidBitmap();
				
				mSelectedImage = decodeSampledBitmapFromFile(filePath,
						captionImgView.getWidth(), captionImgView.getHeight());
				captionImgView.setVisibility(View.VISIBLE);
				captionImgView.setImageBitmap(mSelectedImage);
				btnPhotoFrame.setVisibility(View.GONE);
				mFilePath = filePath;
				fileExtension = "jpg";

			} catch (OutOfMemoryError e) {
				// TODO: handle exception
				Log.v("Create Group", "Path:" + filePath);
			}

		} else if (requestCode == PIC_IMAGE_FROM_GALLERY) {
			if (intent != null && resultCode == RESULT_OK) {

				Uri selectedImage = intent.getData();

				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();

				try {
					hasValidBitmap();
					mSelectedImage = decodeSampledBitmapFromFile(filePath,
							captionImgView.getWidth(),
							captionImgView.getHeight());
					captionImgView.setVisibility(View.VISIBLE);
					captionImgView.setImageBitmap(mSelectedImage);
					btnPhotoFrame.setVisibility(View.GONE);
					mFilePath = filePath;

					int dot = mFilePath.lastIndexOf(".");
					fileExtension = mFilePath.substring(dot + 1);

				} catch (OutOfMemoryError e) {
					// TODO: handle exception
					Log.v("Create Group", "Path:" + filePath);
				}
			}
		}
	}

	private synchronized boolean hasValidBitmap() {
		return mSelectedImage != null && !mSelectedImage.isRecycled();
	}

	/**
	 * Decode and sample down a bitmap from a file to the requested width and
	 * height.
	 * 
	 * @param filename
	 *            The full path of the file to decode
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @param cache
	 *            The ImageCache used to find candidate bitmaps for use with
	 *            inBitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public static Bitmap decodeSampledBitmapFromFile(String filename,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filename, options);
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
	 * object when decoding bitmaps using the decode* methods from
	 * {@link BitmapFactory}. This implementation calculates the closest
	 * inSampleSize that will result in the final decoded bitmap having a width
	 * and height equal to or larger than the requested width and height. This
	 * implementation does not ensure a power of 2 is returned for inSampleSize
	 * which can be faster when decoding but results in a larger bitmap which
	 * isn't as useful for caching purposes.
	 * 
	 * @param options
	 *            An options object with out* params already populated (run
	 *            through a decode* method with inJustDecodeBounds==true
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee a final image
			// with both dimensions larger than or equal to the requested height
			// and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		default:
			break;
		}
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		Log.i("Response", "as");
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		UIUtilities.hideKeyboard(this, captionTxt);
		if (this.msgType == ResponseMesssagType.PhotoCaptionMessageType) {
			
			captionImgView.setVisibility(View.GONE);
			captionTxt.setText("");
			btnPhotoFrame.setVisibility(View.VISIBLE);
			mFilePath = null;
			mSelectedImage = null;

			showSuccessAlert();
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(this, ero.getErrorText());
			} else
				UIUtilities.showServerError(this);

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(this);
		} else {
			UIUtilities.showServerError(this);
		}

		Log.i("Response", "as");
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub

		UIUtilities.showErrorWithOkButton(this, ex.getMessage());

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		String error = "Error";

		if (items != null && items.size() > 0) {
			ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
			error = ero.getErrorText();
		}
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, error,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	void showAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information, R.string.under_construction,
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

	void showSuccessAlert() {
		UIUtilities.showConfirmationAlert(this,
				R.string.dialog_title_information,
				"Your caption with snap is uploaded successfully!",
				R.string.dialog_button_Ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
	}

}
