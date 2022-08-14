package cn.zeppin.product.ntb.ui.main.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geng.library.commonutils.NetWorkUtils;
import com.lidong.pdf.PDFView;
import com.lidong.pdf.listener.OnDrawListener;
import com.lidong.pdf.listener.OnLoadCompleteListener;
import com.lidong.pdf.listener.OnPageChangeListener;

import butterknife.Bind;
import cn.zeppin.product.ntb.R;
import cn.zeppin.product.ntb.app.AppConstant;
import cn.zeppin.product.ntb.app.BaseActivity;

/**
 * pdf在线预览
 */
public class PdfViewActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.pdfView)
    PDFView pdfView;
    private String mUri = "https://";
    private String mTitle = "协议";

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!NetWorkUtils.isNetConnected(PdfViewActivity.this)) {
            showNetErrorTip();
            tvTitle.setText("网络无法连接");
            return;
        }
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mUri = bundle.getString(AppConstant.INTENT_PDF_URL);
                mTitle = bundle.getString(AppConstant.INTENT_PDF_TITLE);
                tvTitle.setText(mTitle);
                displayFromFile1(mUri, mTitle);
            }
        } catch (Exception e) {
            hideProgress();
            showToastCenter("读取失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取打开网络的pdf文件
     *
     * @param fileUrl
     * @param fileName
     */
    private void displayFromFile1(String fileUrl, String fileName) {
        showProgress();
        pdfView.fileFromLocalStorage(this, this, this, fileUrl, fileName);   //设置pdf文件地址

    }

    /**
     * 翻页回调
     *
     * @param page
     * @param pageCount
     */
    @Override
    public void onPageChanged(int page, int pageCount) {
//        Toast.makeText(this, "page= " + page +
//                " pageCount= " + pageCount, Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载完成回调
     *
     * @param nbPages 总共的页数
     */
    @Override
    public void loadComplete(int nbPages) {
//        Toast.makeText(this, "加载完成" + nbPages, Toast.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
        // Toast.makeText( MainActivity.this ,  "pageWidth= " + pageWidth + "
        // pageHeight= " + pageHeight + " displayedPage="  + displayedPage , Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示对话框
     */
    private void showProgress() {
        startProgressDialog("加载中...");
    }

    /**
     * 关闭等待框
     */
    private void hideProgress() {
        stopProgressDialog();
    }
}