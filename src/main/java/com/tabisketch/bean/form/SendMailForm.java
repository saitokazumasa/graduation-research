package com.tabisketch.bean.form;

import com.tabisketch.constant.URL;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SendMailForm {
    @NotBlank
    private String from;

    @NotBlank
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    public static SendMailForm genCreateUserMail(final String from, final String to, final String token) {
        final String subject = "【たびすけっち】メールアドレスの認証をお願いします";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        以下内容で仮登録を受け付けました。
                        
                        ◆ メールアドレス: %s
                        ◆ パスワード: ***
                        
                        **以下URLをクリックして、メールアドレスを認証してください。**
                        %s
                        
                        身に覚えのない場合は、このメールを破棄してください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                to,
                URL.TabisketchDotCom + "/mail/confirm/" + token,
                URL.TabisketchDotCom,
                from
        );
        return new SendMailForm(from, to, subject, content);
    }
}
