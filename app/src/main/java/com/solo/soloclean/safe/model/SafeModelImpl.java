package com.solo.soloclean.safe.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.solo.soloclean.common.utils.AppUtils;
import com.solo.soloclean.common.utils.DeviceUtils;
import com.solo.soloclean.safe.bean.SafeBean;
import com.trustlook.sdk.cloudscan.CloudScanClient;
import com.trustlook.sdk.cloudscan.ScanResult;
import com.trustlook.sdk.data.AppInfo;
import com.trustlook.sdk.data.PkgInfo;
import com.trustlook.sdk.data.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 16-10-27.
 */

public class SafeModelImpl implements SafeModel {

    @Override
    public void cloudScan(Context context) {
        new GenerateAppInfo(context).execute();
    }

    private class GenerateAppInfo extends AsyncTask<Void, String, List<PkgInfo>> {

        private Context mContext;
        private CloudScanClient mClient;
        private List<PkgInfo> mPkgInfoList = new ArrayList<>();

        public GenerateAppInfo(Context context) {
            mContext = context;
            mClient = new CloudScanClient.Builder().setContext(mContext).setConnectionTimeout(3000)
                    .setSocketTimeout(5000).setDeviceId(DeviceUtils.getDeviceId(mContext)).setRegion(Region.INTL)
                    .setToken("1111").setVerbose(1)
                    .build();
        }

        @Override
        protected List<PkgInfo> doInBackground(Void... params) {
            List<PackageInfo> packageInfoList = AppUtils.getLocalAppsPkgInfo(mContext);
            for (PackageInfo pi : packageInfoList) {
                if (pi != null && pi.applicationInfo != null) {
                    PkgInfo info = mClient.populatePkgInfo(pi.packageName, pi.applicationInfo.publicSourceDir);
                    mPkgInfoList.add(info);
                    Log.d("messi", "generate pkg :" + info.getPkgName());
                }
            }
            return mPkgInfoList;
        }

        @Override
        protected void onPostExecute(List<PkgInfo> pkgInfos) {
            //开始云查杀
            if (pkgInfos != null) {
                new CloudScanApps(mContext, mClient).execute(pkgInfos);
            }
        }
    }


    private class CloudScanApps extends AsyncTask<List<PkgInfo>, String, ScanResult> {

        private Context mContext;
        private CloudScanClient mClient;

        public CloudScanApps(Context context, CloudScanClient client) {
            mContext = context;
            mClient = client;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ScanResult doInBackground(List<PkgInfo>... params) {
            if (isCancelled()) {
                return null;
            }

            return mClient.cloudScan(params[0]);
        }

        @Override
        protected void onPostExecute(ScanResult scanResult) {
            if (scanResult != null && scanResult.isSuccess()) {
                List<AppInfo> appInfos = scanResult.getList();
                if (appInfos != null) {
                    for (AppInfo ai : appInfos) {
                        Log.d("messi", "scan pkg :" + ai.getPackageName() + " score :" + ai.getScore());
                        if (ai.getScore() >= 8) {//8-10分为恶意应用
                            String packageName = ai.getPackageName();
                            //TODO:回调
                        }
                    }
                }
            }
        }
    }

    public interface onSafeScanResultListener {
        void onCloudScanSafty();

        void onCloudScanUnSafty(List<SafeBean> safeBeanList);
    }
}
