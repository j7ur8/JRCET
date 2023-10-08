package jrcet.diycomponents;

import jrcet.frame.Asset.Asset;
import jrcet.frame.Dencrypt.Aes.Aes;
import jrcet.frame.Dencrypt.Ascii.Ascii;
import jrcet.frame.Dencrypt.Base.Base;
import jrcet.frame.Dencrypt.Des.Des;
import jrcet.frame.Dencrypt.Hex.Hex;
import jrcet.frame.Dencrypt.Jwt.Jwt;
import jrcet.frame.Dencrypt.Md5.Md5;
import jrcet.frame.Dencrypt.Rsa.Rsa;
import jrcet.frame.Dencrypt.Unicode.Unicode;
import jrcet.frame.Dencrypt.Url.Url;
import jrcet.frame.HText.Alone.Alone;
import jrcet.frame.HText.Format.Format;
import jrcet.frame.HText.IPUnit.IPUnit;
import jrcet.frame.HText.Len.Len;
import jrcet.frame.HText.Parsepy.Parsepy;
import jrcet.frame.HText.Regex.Regex;
import jrcet.frame.HText.Sort.Sort;
import jrcet.frame.Tools.Captcha.Captcha;
import jrcet.help.Helper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static burp.MyExtender.BurpAPI;
import static jrcet.frame.Dencrypt.Aes.AesComponent.AesCipherArea;
import static jrcet.frame.Dencrypt.Aes.AesComponent.AesPlainArea;
import static jrcet.frame.Dencrypt.Ascii.AsciiComponent.AsciiCipherArea;
import static jrcet.frame.Dencrypt.Ascii.AsciiComponent.AsciiPlainArea;
import static jrcet.frame.Dencrypt.Base.BaseComponent.BaseCipherArea;
import static jrcet.frame.Dencrypt.Base.BaseComponent.BasePlainArea;
import static jrcet.frame.Dencrypt.Des.DesComponent.DesCipherArea;
import static jrcet.frame.Dencrypt.Des.DesComponent.DesPlainArea;
import static jrcet.frame.Dencrypt.Hex.HexComponent.HexCipherArea;
import static jrcet.frame.Dencrypt.Hex.HexComponent.HexPlainArea;
import static jrcet.frame.Dencrypt.Jwt.JwtComponent.*;
import static jrcet.frame.Dencrypt.Md5.Md5Component.Md5CipherArea;
import static jrcet.frame.Dencrypt.Md5.Md5Component.Md5PlainArea;
import static jrcet.frame.Dencrypt.Rsa.RsaComponent.RsaCipherArea;
import static jrcet.frame.Dencrypt.Rsa.RsaComponent.RsaPlainArea;
import static jrcet.frame.Dencrypt.Unicode.UnicodeComponent.UnicodeCipherArea;
import static jrcet.frame.Dencrypt.Unicode.UnicodeComponent.UnicodePlainArea;
import static jrcet.frame.Dencrypt.Url.UrlComponent.UrlCipherArea;
import static jrcet.frame.Dencrypt.Url.UrlComponent.UrlPlainArea;
import static jrcet.frame.HText.Alone.AloneComponent.AloneInputArea;
import static jrcet.frame.HText.Alone.AloneComponent.AloneOutputArea;

import static jrcet.frame.HText.Case.CaseComponent.CaseLowerArea;
import static jrcet.frame.HText.Case.CaseComponent.CaseUpperArea;
import static jrcet.frame.HText.Format.FormatComponent.FormatInputArea;
import static jrcet.frame.HText.Format.FormatComponent.FormatOutputArea;
import static jrcet.frame.HText.IPUnit.IPUnitComponent.IPUnitInputArea;
import static jrcet.frame.HText.IPUnit.IPUnitComponent.IPUnitOutputArea;
import static jrcet.frame.HText.Len.LenComponent.LenInputArea;
import static jrcet.frame.HText.Len.LenComponent.LenOutputArea;
import static jrcet.frame.HText.Parsepy.ParsepyComponent.ParsepyInputArea;
import static jrcet.frame.HText.Parsepy.ParsepyComponent.ParsepyOutputArea;
import static jrcet.frame.HText.Regex.RegexComponent.RegexInputArea;
import static jrcet.frame.HText.Regex.RegexComponent.RegexOutputArea;
import static jrcet.frame.HText.Sort.SortComponent.SortInputArea;
import static jrcet.frame.HText.Sort.SortComponent.SortOutputArea;
import static jrcet.frame.Tools.Captcha.CaptchaComponent.*;

public class DiyJButton extends JButton implements  ActionListener {

    public DiyJButton(String text) {
        setText(text);
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(120,30));
        addActionListener(this);
        setFont(new Font("微软雅黑", Font.PLAIN,14));
    }

    @Override
    public void actionPerformed(ActionEvent e){

        switch (((DiyJButton) e.getSource()).getName()) {
            case "CaptchaMenuRequestUrlButton" -> {
                new Captcha.setCaptchaWorker(CaptchaRequestEditor.getRequest()).execute();
            }
            case "CaptchaMenuRequestIdentifyButton" -> {
                Captcha.identifyCaptcha();
            }
            case "AloneExecuteButton" -> {
                AloneOutputArea.setText(Alone.removeDuplication(AloneInputArea.getText()));

            }
            case "CaseLowerButton" -> {
                CaseLowerArea.setText(CaseUpperArea.getText().toLowerCase());

            }
            case "CaseUpperButton" -> {
                CaseUpperArea.setText(CaseLowerArea.getText().toUpperCase());
            }

            case "ParsepyParseButton" -> {
                ParsepyOutputArea.setText(Parsepy.getPYInitial(ParsepyInputArea.getText()));
            }

            case "SortExecuteButton" -> {
                SortOutputArea.setText(Sort.uniq(SortInputArea.getText()));
            }

            case "FormatExecuteButton" -> {
                FormatOutputArea.setText(Format.formatting(FormatInputArea.getText()));
            }

            case "IPUnitPrintButton" -> {
                IPUnitOutputArea.setText(IPUnit.printIpRange(IPUnitInputArea.getText()));
            }

            case "IPUnitBelongButton" -> {
                IPUnitOutputArea.setText(IPUnit.isInRange(IPUnitInputArea.getText()));
            }

            case "RegexExecuteButton" -> {
                RegexOutputArea.setText(Regex.match(RegexInputArea.getText()));
            }

            case "LenExecuteButton" -> {
                LenOutputArea.setText(Len.length(LenInputArea.getText()));
            }

            case "AesFunctionEncryptButton" -> {
                AesCipherArea.setText(Aes.Encrypt(AesPlainArea.getText()));
            }

            case "AesFunctionDecryptButton" -> {
                AesPlainArea.setText(Aes.Decrypt(AesCipherArea.getText()));
            }

            case "DesFunctionEncryptButton" -> {
                DesCipherArea.setText(Des.Encrypt(DesPlainArea.getText()));
            }

            case "DesFunctionDecryptButton" -> {
                DesPlainArea.setText(Des.Decrypt(DesCipherArea.getText()));
            }

            case "Md5FunctionEncryptButton" -> {
                Md5CipherArea.setText(Md5.stringToMd5(Md5PlainArea.getText()));
            }

            case "RsaFunctionEncryptButton" ->{
                RsaCipherArea.setText(Rsa.encrypt(RsaPlainArea.getText()));
            }

            case "RsaFunctionDecryptButton" ->{
                RsaPlainArea.setText(Rsa.decrypt(RsaCipherArea.getText()));
            }

            case "HexFunctionEncryptButton" -> {
                HexCipherArea.setText(Hex.encrypt(HexPlainArea.getText()));
            }

            case "HexFunctionDecryptButton" -> {
                HexPlainArea.setText(Hex.decrypt(HexCipherArea.getText()));
            }

            case "UrlFunctionEncryptButton" -> {
                UrlCipherArea.setText(Url.encodeAll(UrlPlainArea.getText()));
            }

            case "UrlFunctionDecryptButton" -> {
                UrlPlainArea.setText(Url.decode(UrlCipherArea.getText()));
            }

            case "AsciiFunctionEncryptButton" -> {
                AsciiCipherArea.setText(Ascii.encrypt(AsciiPlainArea.getText()));
            }

            case "AsciiFunctionDecryptButton" -> {
                AsciiPlainArea.setText(Ascii.decrypt(AsciiCipherArea.getText()));
            }

            case "UnicodeFunctionEncryptButton" -> {
                UnicodeCipherArea.setText(Unicode.stringToUnicode(UnicodePlainArea.getText()));
            }

            case "UnicodeFunctionDecryptButton" -> {
                UnicodePlainArea.setText(Unicode.unicodeToString(UnicodeCipherArea.getText()));
            }

            case "BaseFunctionEncryptButton" -> {
                BaseCipherArea.setText(Base.encrypt(BasePlainArea.getText()));
            }

            case "BaseFunctionDecryptButton" -> {
                BasePlainArea.setText(Base.decrypt(BaseCipherArea.getText()));
            }

            case "JwtFunctionEncryptButton" -> {
                JwtCipherArea.setText(Jwt.encrypt(JwtPlainHeaderArea.getText(),JwtPlainPayloadArea.getText(), JwtPlainSecretArea.getText(),JwtPlainPrivateArea.getText()));
            }

            case "JwtFunctionVerifyButton" -> {
                String JwtToken = JwtCipherArea.getText();
                String header = JwtToken.substring(0,JwtToken.lastIndexOf("."));
                String payload = JwtToken.substring(JwtToken.lastIndexOf(".")+1);
                if(Jwt.verify(JwtToken,JwtPlainHeaderArea.getText(),JwtPlainPayloadArea.getText(), JwtPlainSecretArea.getText(), JwtPlainPrivateArea.getText(),JwtPlainPublicArea.getText())){
                    Jwt.getJwtMenuVerifyLabel().setText("signature verified");
                    Jwt.getJwtMenuVerifyLabel().setForeground(new Color(80,183,236));
                    JwtPlainHeaderArea.setText(Helper.base64UrlDecode2String(header));
                    JwtPlainPayloadArea.setText(Helper.base64Encode2String(payload));
                }else{
                    Jwt.getJwtMenuVerifyLabel().setText("invalid signature");
                    Jwt.getJwtMenuVerifyLabel().setForeground(Color.red);
                }

            }

            case "AssetMainBodyControlLastButton" -> {
                Asset.lastPage();
            }

            case "AssetMainBodyControlNextButton" -> {
                Asset.nextPage();
            }
        }

    }

}
