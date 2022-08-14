package cn.zeppin.product.ntb.ui.user.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geng.library.baserx.RxBus;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppApplication;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;
import cn.zeppin.product.ntb.bean.ImgResource;
import cn.zeppin.product.ntb.bean.User;
import cn.zeppin.product.ntb.permission.PermissionInfo;
import cn.zeppin.product.ntb.permission.PermissionOriginResultCallBack;
import cn.zeppin.product.ntb.permission.PermissionUtil;
import cn.zeppin.product.ntb.ui.user.contract.CertificationContract;
import cn.zeppin.product.ntb.ui.user.model.CertificationModel;
import cn.zeppin.product.ntb.ui.user.presenter.CertificationPresenter;
import cn.zeppin.product.ntb.utils.ButtonUtils;
import cn.zeppin.product.ntb.utils.IDCardUtil;
import cn.zeppin.product.ntb.utils.ImageUtil;
import cn.zeppin.product.ntb.widget.ClearEditText;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.zeppin.product.ntb.app.ApiConstants.RESOURCE_HOST;

/**
 * 实名认证
 */
public class CertificationActivity extends BaseActivity<CertificationPresenter, CertificationModel> implements CertificationContract.View, View.OnClickListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.cet_name)
    ClearEditText cetName;
    @Bind(R.id.tv_idCard)
    TextView tvIdCard;
    @Bind(R.id.cet_idCard)
    ClearEditText cetIdCard;
    @Bind(R.id.tv_front)
    TextView tvFront;
    @Bind(R.id.img_front)
    ImageView imgFront;
    @Bind(R.id.ll_idCardFront)
    LinearLayout llIdCardFront;
    @Bind(R.id.tv_reverse)
    TextView tvReverse;
    @Bind(R.id.img_reverse)
    ImageView imgReverse;
    @Bind(R.id.ll_idCardReverse)
    LinearLayout llIdCardReverse;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;

    private User user = null;
    private ImgResource faceResource;
    private ImgResource backResource;
    private boolean isFace = true;//是否正面

    @Override
    public int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        tvTitle.setText("实名认证");
        ivBack.setOnClickListener(this);
        llIdCardFront.setOnClickListener(this);
        llIdCardReverse.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

//        cetName.setTypeface(AppApplication.mTypefaceLight);
//        cetIdCard.setTypeface(AppApplication.mTypefaceLight);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            User user = (User) bundle.getSerializable(AppConstant.INTENT_USER);
            if (user != null) {
                this.user = user;
                cetName.setText(user.getRealname());
                cetName.setTextColor(getResources().getColor(R.color.alpha_70_black));
                cetName.setCursorVisible(false);      //设置输入框中的光标不可见
                cetName.setFocusable(false);           //无焦点
                cetName.setFocusableInTouchMode(false);     //触摸时也得不到焦点

                cetIdCard.setText(user.getIdcard());
                cetIdCard.setTextColor(getResources().getColor(R.color.alpha_70_black));
                cetIdCard.setCursorVisible(false);      //设置输入框中的光标不可见
                cetIdCard.setFocusable(false);           //无焦点
                cetIdCard.setFocusableInTouchMode(false);     //触摸时也得不到焦点
            }
        }
    }


    @Override
    public void onClick(View v) {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_idCardFront://上传正面照
                PermissionUtil.getInstance().request(this, permissions, new PermissionOriginResultCallBack() {
                    @Override
                    public void onResult(List<PermissionInfo> acceptList, List<PermissionInfo> rationalList, List<PermissionInfo> deniedList) {
                        Log.i("", acceptList.toString() + rationalList.toString() + deniedList.toString());

                        if (PermissionUtil.PERMISSION_GRANTED
                                != PermissionUtil.getInstance().checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showToastCenter(getResources().getString(R.string.permission_check_fail));
                        } else {
                            isFace = true;
                            photoDialog();
                        }
                    }
                });
                break;
            case R.id.ll_idCardReverse://上传反面照
                PermissionUtil.getInstance().request(this, permissions, new PermissionOriginResultCallBack() {
                    @Override
                    public void onResult(List<PermissionInfo> acceptList, List<PermissionInfo> rationalList, List<PermissionInfo> deniedList) {
                        Log.i("", acceptList.toString() + rationalList.toString() + deniedList.toString());

                        if (PermissionUtil.PERMISSION_GRANTED
                                != PermissionUtil.getInstance().checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showToastCenter(getResources().getString(R.string.permission_check_fail));
                        } else {
                            isFace = false;
                            photoDialog();
                        }
                    }
                });
                break;
            case R.id.btn_confirm://提交认证
                if (!ButtonUtils.isFastDoubleClick(R.id.btn_confirm)) {
                    submit();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交认证
     */
    private void submit() {
        if (TextUtils.isEmpty(cetName.getText())) {
            showToastCenter("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(cetIdCard.getText())) {
            showToastCenter("请填写身份证号码");
            return;
        }
//        if (faceResource == null) {
//            showToastCenter("请上传身份证正面照片");
//            return;
//        }
//        if (backResource == null) {
//            showToastCenter("请上传身份证反面照片");
//            return;
//        }
        String faceUuid = "";
        String backUuid = "";
        if (faceResource != null) {
            faceUuid = faceResource.getUuid();
        }
        if (backResource != null) {
            backUuid = faceResource.getUuid();
        }

        String uuid = AppApplication.getInstance().getUuid();
        if (user != null) {
            mPresenter.certificationImg(uuid, faceUuid, backUuid);
        } else {
            String result = IDCardUtil.IDCardValidate(cetIdCard.getText().toString());
            if (!"".equals(result)) {
                showToastCenter("无效的身份证号码");
                return;
            }
            mPresenter.certification(uuid, cetName.getText().toString(), cetIdCard.getText().toString(), faceUuid, backUuid);
        }
    }

    private void photoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                //设置两个item
                .setItems(new String[]{"相册", "拍照"}, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //调用系统相册功能：
                                ImageUtil.openLocalImage(CertificationActivity.this);
                                break;
                            case 1:
                                //调用系统拍照功能：
                                ImageUtil.openCameraImage(CertificationActivity.this);
                                break;
                            default:
                                break;
                        }

                    }
                });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageUtil.GET_IMAGE_FROM_PHONE://相册
                if (resultCode != Activity.RESULT_CANCELED) {
                    if (data.getData() != null)
                        takePicResult(data.getData());
                }
                break;
            case ImageUtil.GET_IMAGE_BY_CAMERA://拍照
                if (resultCode == Activity.RESULT_CANCELED) {
                    ImageUtil
                            .deleteImageUri(CertificationActivity.this, ImageUtil.imageUriFromCamera);
                } else {
                    takePicResult(ImageUtil.imageUriFromCamera);
                }
                break;
            default:
                break;
        }

    }


    private void takePicResult(final Uri uri) {
        final File file = ImageUtil
                .getFileByUri(uri, this);
        Luban.get(this)
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if (file != null && file.length() > 10) {
                            String uuid = AppApplication.getInstance().getUuid();
                            if (!TextUtils.isEmpty(uuid)) {
                                mPresenter.uploadImage(file, isFace, uri);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                    }
                }).launch();    //启动压缩


    }

    @Override
    public void showLoading(String title) {
        startProgressDialog(title);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        showToastCenter(msg);
    }

    @Override
    public void loadImgface(ImgResource imgResource, Uri uri) {
        //正面
        faceResource = imgResource;
        String url = RESOURCE_HOST + imgResource.getUrl();
        Glide.with(this).load(url).into(imgFront);
        imgFront.setVisibility(View.VISIBLE);
        tvFront.setVisibility(View.GONE);
        //删除多余图片
//        ImageUtil.deleteImg(this, uri);
    }

    @Override
    public void loadImgBack(ImgResource imgResource, Uri uri) {
        //背面
        backResource = imgResource;
        String url = RESOURCE_HOST + imgResource.getUrl();
        Glide.with(this).load(url).into(imgReverse);
        imgReverse.setVisibility(View.VISIBLE);
        tvReverse.setVisibility(View.GONE);
        //删除多余图片
//        ImageUtil.deleteImg(this, uri);

    }

    @Override
    public void submitSuccess() {
        //提交成功
        startActivity(CertificationYesActivity.class);
        RxBus.getInstance().post(AppConstant.REFRESH_USER, true);//刷新用户信息
        finish();
    }

    @Override
    public void submitFailed(String msg) {
        //认证未通过，重新认证
        Bundle bundle = new Bundle();
        bundle.putString("errorMsg", msg);
        startActivity(CertificationNoActivity.class, bundle);
    }

}
