package kdmc_kumar.Notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.Iterator;

import displ.mobydocmarathi.com.R;
import displ.mobydocmarathi.com.R.id;
import displ.mobydocmarathi.com.R.layout;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Notification.NotificationAdapter.MyViewHolder;

/**
 * Created by Ponnusamy M on 4/24/2017.
 */

public class NotificationAdapter extends Adapter<MyViewHolder> {

    private ArrayList<CommonDataObjects.NotificationGetSet> notificationGetSetArrayAdapter = new ArrayList<>();

    public NotificationAdapter(ArrayList<CommonDataObjects.NotificationGetSet> notificationGetSetArrayAdapter) {
        this.notificationGetSetArrayAdapter = notificationGetSetArrayAdapter;
    }

    @NonNull
    @Override
    public final NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout.notification_list_row, parent, false);
        return new NotificationAdapter.MyViewHolder(view);
    }

    @Override
    public final void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {

        StringBuilder stringBuilder = new StringBuilder();

        CommonDataObjects.NotificationGetSet item = this.notificationGetSetArrayAdapter.get(position);

        ArrayList<CommonDataObjects.NotificationListGetSet> tabledata = item.getNotificationListGetSets();

        String patient_id = item.get_patientId();
        String patient_name = item.get_patientName();
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.setLayerType(View.LAYER_TYPE_NONE, null);
        holder.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        holder.webView.getSettings().setRenderPriority(RenderPriority.HIGH);
        holder.webView.getSettings().setDefaultTextEncodingName("utf-8");

        holder.webView.setWebChromeClient(new NotificationAdapter.MyWebChromeClient());

        holder.webView.setBackgroundColor(0x00000000);
        holder.webView.setVerticalScrollBarEnabled(true);
        holder.webView.setHorizontalScrollBarEnabled(true);

        holder.webView.getSettings().setJavaScriptEnabled(true);

        holder.webView.getSettings().setAllowContentAccess(true);

        holder.webView.addJavascriptInterface(new NotificationAdapter.WebAppInterface(holder.webView.getContext()), "android");


        for (Iterator<CommonDataObjects.NotificationListGetSet> iterator = tabledata.iterator(); iterator.hasNext(); ) {
            CommonDataObjects.NotificationListGetSet notificationListGetSet = iterator.next();
            stringBuilder.append("<tr>\n" + "    <td>").append(notificationListGetSet.get_testName()).append("</td>\n").append("\t<td>").append(notificationListGetSet.get_testValue()).append("</td>\n").append("\t<td>").append(notificationListGetSet.get_testRemark()).append("</td>\n").append("  </tr>");
        }

        int value_position = position + 1;

        String mime = "text/html";
        String encoding = "utf-8";
        holder.webView.loadDataWithBaseURL(null, ("<!DOCTYPE html>\n" + '\n' + "<html lang=\"en\">\n" + "<head>\n" + '\n' + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" + "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" + '\n' + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" + '\n' + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" + '\n' + "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" + '\n' + "</head>\n" + "<body>  \n" + " \n" + "\n<font class=\"sub\">  <i class=\"fa \" aria-hidden=\"true\"></i> ") + value_position + ". " + item.get_mTestId() + " </font>" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr >\n" + "<td height=\"15\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>  Patient Name</b></td> \n" + "<td height=\"15\" style=\"color:#000000;\">:  " + patient_name + "</td> \n" + "</tr>\n" + "<tr >\n" + "<td height=\"15\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>  Patient Id</b></td> \n" + "<td height=\"15\" style=\"color:#000000;\">:   " + patient_id + "</td> \n" + "</tr>\n" + "</tbody>\n" + "</table>\n" + '\n' + "<div class=\"table-responsive\">          \n" + "<table class=\"table\">\n" + "  <tr>\n" + "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">Test Name</font></th>\n" + "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Test Value</font></th>\n" + "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">Test Remarks</font></th>\n" + '\n' + "  </tr>\n" + stringBuilder + '\n' + "</table>\n" + '\n' + '\n' + "</div>\n" + '\n' + "<!----------------------------------------------------------------->\n" + "</body>\n" + "</html>       ", mime, encoding, null);
        //  holder.webView.loadUrl(sb.toString());

    }

    @Override
    public final int getItemCount() {
        return this.notificationGetSetArrayAdapter.size();
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    static class MyViewHolder extends ViewHolder {
        final WebView webView;

        MyViewHolder(View itemView) {
            super(itemView);

            this.webView = itemView.findViewById(id.webview);
        }
    }

    static class WebAppInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            this.mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }
}
