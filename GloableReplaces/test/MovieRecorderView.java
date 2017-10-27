package test;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioEncoder;
import android.media.MediaRecorder.AudioSource;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OutputFormat;
import android.media.MediaRecorder.VideoEncoder;
import android.media.MediaRecorder.VideoSource;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ciii.generate.baseim.Global;
import com.ciii.generate.baseim.IMApp;
import com.ciii.generate.baseim.R;
import com.ciii.generate.baseim.utils.CamParamUtil;
import com.ciii.generate.baseim.utils.LogUtil;
import com.ciii.generate.baseim.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 视频播放控件
 *
 */
public class MovieRecorderView extends LinearLayout implements OnErrorListener {

    private static String TAG = "MovieRecorderView";
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private ProgressBar mProgressBar;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
//    private Timer mTimer;// 计时器
    private OnRecordFinishListener mOnRecordFinishListener;// 录制完成回调接口

    private int mWidth;// 视频分辨率宽度
    private int mHeight;// 视频分辨率高度
    private boolean isOpenCamera;// 是否一开始就打开摄像头
    private int mRecordMaxTime;// 一次拍摄最长时间
    private int mTimeCount;// 时间计数
    private File mRecordFile = null;// 文件

    public MovieRecorderView(Context context) {
        this(context, null);
    }

    public MovieRecorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovieRecorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 初始化各项组件
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieRecorderView, defStyle, 0);
        mWidth = a.getInteger(R.styleable.MovieRecorderView_video_width, 1080);// 默认320
        mHeight = a.getInteger(R.styleable.MovieRecorderView_video_height, 720);// 默认240

        isOpenCamera = a.getBoolean(R.styleable.MovieRecorderView_is_open_camera, true);// 默认打开
        mRecordMaxTime = a.getInteger(R.styleable.MovieRecorderView_record_max_time, 10);// 默认为10

        LayoutInflater.from(context).inflate(R.layout.movie_recorder_view, this);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setMax(mRecordMaxTime);// 设置进度条最大量
        mProgressBar.setVisibility(View.INVISIBLE);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mCustomCallBack);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        a.recycle();
    }

    private CustomCallBack mCustomCallBack = new CustomCallBack();

    /**
     *
     */
    private class CustomCallBack implements Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            try {
                initCamera();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (!isOpenCamera)
                return;
            LogUtil.LogShow(TAG, "surfaceDestroyed start");
            freeCameraResource();
            LogUtil.LogShow(TAG, "surfaceDestroyed end");
        }
    }

    private int mVideoWidth, mVideoHeight;

    /**
     * 初始化摄像头
     *
     * @date 2015-2-5
     * @throws IOException
     */
    private void initCamera() throws IOException {

        LogUtil.LogShow(TAG, "initCamera");
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;

        // setCameraParams();

        Parameters parameters = mCamera.getParameters();
//        int width = UIUtil.getScreenSize(getContext()).x;
//        int height = width / 4 * 3;
        float previewRate = 4.0f / 3.0f;

        Camera.Size previewSize = CamParamUtil.getInstance().getPropPreviewSize(
                parameters.getSupportedPreviewSizes(), previewRate, 800);

//        Camera.Size size = getBestPreviewSize(height, width, parameters);
        if (previewSize != null) {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
        }

        Camera.Size size = getBestVideoSize(1280, 720, mCamera.getParameters());
        mVideoWidth = size.width;
        mVideoHeight = size.height;

        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains("continuous-video")) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        } else {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对
        }

        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        mCamera.setPreviewDisplay(mSurfaceHolder);

        mCamera.startPreview();
//        mCamera.unlock();
    }

    private Camera.Size getBestVideoSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            LogUtil.LogShow(TAG, "video list:w = " + size.width + "h = " + size.height);
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        LogUtil.LogShow(TAG, "getBestVideoSize:w = " + result.width + "h = " + result.height);

        return result;
    }


    /**
     * 设置摄像头为竖屏
     *
     * @date 2015-2-5
     */
    /*private void setCameraParams() {
        if (mCamera != null) {
            Parameters params = mCamera.getParameters();
            params.set("orientation", "portrait");
            mCamera.setParameters(params);
        }
    }*/

    /**
     * 释放摄像头资源
     *
     */
    private void freeCameraResource() {
        LogUtil.LogShow(TAG, "freeCameraResource start");
        if(mCustomCallBack != null && mSurfaceHolder != null){
            mSurfaceHolder.removeCallback(mCustomCallBack);
        }

        LogUtil.LogShow(TAG, "freeCameraResource 2");
        try{
            if (mCamera != null) {
                LogUtil.LogShow(TAG, "freeCameraResource setPreviewCallback");
                mCamera.setPreviewCallback(null);
                LogUtil.LogShow(TAG, "freeCameraResource stopPreview");
                mCamera.stopPreview();
//            mCamera.lock();
                LogUtil.LogShow(TAG, "freeCameraResource release");
                mCamera.release();
                mCamera = null;
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.LogShow(TAG, "freeCameraResource e =" + e.getMessage());
        }
        LogUtil.LogShow(TAG, "freeCameraResource end");
    }

    private void createRecordDir() {
        String directory = Global.CACHE_DIR;
        File sampleDir = new File(directory + "/video/");
        if (!sampleDir.exists()) {
            sampleDir.mkdirs();
        }
        File vecordDir = sampleDir;
        // 创建文件
        try {
            mRecordFile = File.createTempFile("recording", ".m", vecordDir); //mp4格式
            LogUtil.LogShow("TAG",mRecordFile.getAbsolutePath());
        } catch (IOException e) {
        }
    }


    /**
     * 初始化
     *
     * @date 2015-2-5
     * @throws IOException
     */
    private boolean initRecord() throws IOException {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        if (mCamera != null){
            try{
                mCamera.unlock();
            }catch (Exception e){

            }
            mMediaRecorder.setCamera(mCamera);
        }

        mMediaRecorder.setOnErrorListener(this);
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        mMediaRecorder.setVideoSource(VideoSource.CAMERA);// 视频源
        mMediaRecorder.setAudioSource(AudioSource.MIC);// 音频源
        mMediaRecorder.setOutputFormat(OutputFormat.THREE_GPP);// 视频输出格式
        mMediaRecorder.setAudioEncoder(AudioEncoder.AAC);// 音频格式
        mMediaRecorder.setAudioChannels(2);
        mMediaRecorder.setAudioSamplingRate(44100);



        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        mMediaRecorder.setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);

//        mMediaRecorder.setVideoSize(mVideoWidth, mVideoHeight);

//        mMediaRecorder.setVideoSize(mWidth, mHeight);// 设置分辨率：不行
//         mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
        mMediaRecorder.setVideoEncodingBitRate((int)(3*1024*1024));// 设置帧频率，然后就清晰了
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制
        mMediaRecorder.setVideoEncoder(VideoEncoder.H264);// 视频录制格式
        // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
        mMediaRecorder.setOutputFile(mRecordFile.getAbsolutePath());

//        boolean needDiscard = false;
//        if(!checkPermission()){
//            needDiscard = true;
//            LogUtil.LogShow("test", "checkPermission = false" );
//        }else{
//            LogUtil.LogShow("test", "checkPermission = true" );
//        }

        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //无权限
//        if(needDiscard){
//            try {
//                mMediaRecorder.stop();
//            }catch (Exception e){
//            }
//            return false;
//        }
        return true;
    }

    public static boolean checkPermission() {
        if (IMApp.instance().checkCallingPermission("android.permission.RECORD_AUDIO")
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    boolean mIsRecording = false;
    public boolean isRecording(){
        return mIsRecording;
    }

    private Handler mHandler = new Handler();
    /**
     * 开始录制视频
     *
     * @param
     *
     * @param onRecordFinishListener
     *            达到指定时间之后回调接口
     */
    public void record(final OnRecordFinishListener onRecordFinishListener) {
        this.mOnRecordFinishListener = onRecordFinishListener;
        createRecordDir();
        try {
            if (!isOpenCamera)// 如果未打开摄像头，则打开
                initCamera();
            boolean success = initRecord();
            if(success){
                mIsRecording = true;
                mTimeCount = 0;// 时间计数器重新赋值
                if(mProgressBar.getVisibility() != View.VISIBLE){
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTimeCount++;
                        mProgressBar.setProgress(mTimeCount);// 设置进度条
                        if (mTimeCount == mRecordMaxTime) {// 达到指定时间，停止拍摄
                            stop();
                            if (mOnRecordFinishListener != null)
                                mOnRecordFinishListener.onRecordFinish();
                        }else{
                            mHandler.postDelayed(this, 1000);
                        }
                    }
                },1000);
            }else{
                ToastUtil.showToast("请重新录制");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止拍摄
     *
     */
    public void stop() {
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);

        if(!mIsRecording){
            return;
        }
        LogUtil.LogShow(TAG, "stop stopRecord");
        stopRecord();
        LogUtil.LogShow(TAG, "stop releaseRecord");
        releaseRecord();
        LogUtil.LogShow(TAG, "stop freeCameraResource");
        freeCameraResource();
    }

    /**
     * 停止录制
     *
     */
    public void stopRecord() {
        LogUtil.LogShow(TAG, "stopRecord start");
        mIsRecording = false;
        mProgressBar.setProgress(0);

        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);

        if (mMediaRecorder != null) {
            // 设置后不会崩
            LogUtil.LogShow(TAG, "stopRecord setOnErrorListener");
            mMediaRecorder.setOnErrorListener(null);
            try {
                LogUtil.LogShow(TAG, "stopRecord stop");
                mMediaRecorder.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogUtil.LogShow(TAG, "stopRecord setPreviewDisplay");
            mMediaRecorder.setPreviewDisplay(null);
        }
        LogUtil.LogShow(TAG, "stopRecord end");
    }

    /**
     * 释放资源
     *
     */
    public void releaseRecord() {
        LogUtil.LogShow(TAG, "releaseRecord start");
        if (mMediaRecorder != null) {
            mMediaRecorder.setOnErrorListener(null);
            LogUtil.LogShow(TAG, "releaseRecord reset");
            mMediaRecorder.reset();
            try {
                LogUtil.LogShow(TAG, "releaseRecord release");
                mMediaRecorder.release();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMediaRecorder = null;
        LogUtil.LogShow(TAG, "releaseRecord end");
    }

    public int getTimeCount() {
        return mTimeCount;
    }

    /**
     * @return the mVecordFile
     */
    public File getmRecordFile() {
        return mRecordFile;
    }

    /**
     * 录制完成回调接口
     *
     */
    public interface OnRecordFinishListener {
        public void onRecordFinish();
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        try {
            if (mr != null)
                mr.reset();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
