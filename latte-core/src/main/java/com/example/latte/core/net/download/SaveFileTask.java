package com.example.latte.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;


import com.example.latte.core.app.Latte;
import com.example.latte.core.net.callBack.IProgress;
import com.example.latte.core.net.callBack.IRequest;
import com.example.latte.core.net.callBack.ISuccess;
import com.example.latte.core.util.file.FileUtil;
import com.example.latte.core.util.log.LatteLogger;


import java.io.File;

import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2017/11/23.
 */

/**
 * 保存文件的AsyncTask，若是APK自动启动安装
 */
public class SaveFileTask extends AsyncTask<Object, Integer, File> {
    public interface IProgressListener {
        void onPublish(int progress);
    }

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IProgress PROGRESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUCCESS, IProgress PROGRESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.PROGRESS = PROGRESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        //两个参数分别是：文件，请求体
        final File file = (File) params[0];
        final ResponseBody body = (ResponseBody) params[1];

        return FileUtil.writeToDisk(body, file, new IProgressListener() {
            @Override
            public void onPublish(int progress) {
                publishProgress(progress);
            }
        });

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (PROGRESS != null) {
            PROGRESS.onProgress(progress);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }

        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);

    }

    //自动安装apk
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            //uri要换为你要打开的文件绝对路径  http://blog.csdn.net/luzhenyuxfcy/article/details/48466561
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }
}

