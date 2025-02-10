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

    public static SendMailForm genCreateUserMail(final String from, final String to, final String uuid) {
        final String subject = "【たびすけっち】メールアドレスの認証をお願いします";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        以下内容で仮登録を受け付けました。
                        
                        ◆ メールアドレス: %s
                        ◆ パスワード: ***
                        
                        **以下URLをクリックして、メールアドレスを認証してください。**
                        %s
                        
                        このリンクの有効期限は30分です。
                        身に覚えのない場合は、このメールを破棄してください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                to,
                URL.TabisketchDotCom + "/register/" + uuid,
                URL.TabisketchDotCom,
                from
        );
        return new SendMailForm(from, to, subject, content);
    }

    public static SendMailForm genResetPasswordMail(final String from, final String to, final String uuid) {
        final String subject = "【【たびすけっち】パスワードの再設定をお願いします";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        パスワードリセットを受け付けました。
                        
                        **以下URLをクリックして、パスワードの再設定をしてください。**
                        %s
                        
                        このリンクの有効期限は30分です。
                        身に覚えのない場合は、このメールを破棄してください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                URL.TabisketchDotCom + "/reset-password/reset/" + uuid,
                URL.TabisketchDotCom,
                from
        );
        return new SendMailForm(from, to, subject, content);
    }

    public static SendMailForm genCompleteResetPasswordMail(final String from, final String to) {
        final String subject = "【【たびすけっち】パスワードが変更されました";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        以下内容で変更を受け付けました。
                        
                        ◆ 新しいパスワード: ***
                        
                        身に覚えのない場合は、お手数をおかけしますが
                        以下お問い合わせよりご連絡ください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                URL.TabisketchDotCom,
                from
        );
        return new SendMailForm(from, to, subject, content);
    }
}
