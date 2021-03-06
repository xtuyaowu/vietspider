/***************************************************************************
 * Copyright 2001-2011 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package org.vietspider.content.translate;

import java.util.ListResourceBundle;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Control;
import org.vietspider.client.ClientPlugin;
import org.vietspider.ui.services.ClientRM;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Nov 19, 2011  
 */
public class TranslationPlugin extends ClientPlugin {

  private static ClientRM clientRM;

  private String label;
  //private String confirm;
  private int type = CONTENT;

  public TranslationPlugin() {
    ClientRM resources = getResources();
    label = resources.getLabel(TranslationPlugin.class.getName() + ".itemTranslation");
    //  confirm = resources.getLabel(getClass().getName() + ".msgAlertSend");
    enable = true;
  }

  public String getConfirmMessage() { return null; }

  public String getLabel() { return label; }

  @Override
  public boolean isValidType(int type_) {
    this.type = type_;
    if(type == CONTENT) return true;
    return false; 
  }

  @Override
  public void invoke(Object... objects) {
    if (!enable || values == null || values.length < 1) return;
    final Browser control = (Browser) objects[1];
    String url = control.getUrl();
    int idx = url.indexOf("/DETAIL/");
    if(idx < 0) return;
    url = url.substring(0, idx) + "/TRANSLATOR/" + values[0];
    control.setUrl(url);
  }

  public boolean isSetup() { return true; }

  public void invokeSetup(Object...objects) {
    if(objects == null || objects.length < 1) return;

    final Control link = (Control) objects[0];
    new BingTranslatorSetup(link.getShell());
  }


  static synchronized ClientRM getResources() {
    if(clientRM != null) return clientRM;

    clientRM = new ClientRM(new ListResourceBundle() {
      protected Object[][] getContents() {
        String packageName  = "org.vietspider.content.translate.";
        return new String[][] {
            {packageName + "TranslationPlugin.itemTranslation",  "Dịch nội dung (Tự thiết lập)"},
            {packageName + "TranslationPlugin.itemTranslationToVi",  "Dịch sang tiếng Việt"},
            {packageName + "TranslationPlugin.itemTranslationToEn",  "Dịch sang tiếng Anh"},
            {packageName + "TranslationPlugin.itemTranslationToCn",  "Dịch sang tiếng Trung"},
            {packageName + "BingTranslatorSetup.title", "Cài đặt máy dịch Bing"},
            {packageName + "BingTranslatorSetup.lblAppID", "Application ID:"},
            {packageName + "BingTranslatorSetup.lblFrom", "Dịch từ tiếng: "},
            {packageName + "BingTranslatorSetup.lblTo", "Sang tiếng:"},            
            {packageName + "BingTranslatorSetup.translationType", "Kiểu hiển thị"},
            {packageName + "BingTranslatorSetup.butOk", "Lưu"},
            {packageName + "BingTranslatorSetup.butOkTip", ""},
            {packageName + "BingTranslatorSetup.butClose", "Đóng"},
            {packageName + "BingTranslatorSetup.butCloseTip", ""},
            {packageName + "BingTranslatorSetup.butClear", "Làm lại"},
            {packageName + "BingTranslatorSetup.butClearTip", ""}
        };
      }
    });
    return clientRM;
  }
}
