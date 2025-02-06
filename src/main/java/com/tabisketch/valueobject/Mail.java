package com.tabisketch.valueobject;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Mail {
    private final String fromMailAddress;
    private final String toMailAddress;
    private final String subject;
    private final String content;

    private Mail(
            final String fromMailAddress,
            final String toMailAddress,
            final String subject,
            final String content
    ) {
        this.fromMailAddress = fromMailAddress;
        this.toMailAddress = toMailAddress;
        this.subject = subject;
        this.content = content;
    }

    /**
     * メールアドレス認証メール（新規登録）を生成する
     */
    public static Mail mailAddressAuthMail(
            final String siteURL,
            final String fromMailAddress,
            final String toMailAddress,
            final UUID token
    ) {
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
                toMailAddress,
                siteURL + "/mail/confirm/" + token.toString(),
                siteURL,
                fromMailAddress
        );
        return new Mail(fromMailAddress, toMailAddress, subject, content);
    }

    /**
     * メールアドレス認証メール（メールアドレス編集）を生成する
     */
    public static Mail mailAddressEditMail(
            final String siteURL,
            final String fromMailAddress,
            final String toMailAddress,
            final UUID token
    ) {
        final String subject = "【たびすけっち】メールアドレスの認証をお願いします";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        以下内容で変更を受け付けました。
                        
                        ◆ 新しいメールアドレス: %s
                        
                        **以下URLをクリックして、メールアドレスを認証してください。**
                        %s
                        
                        身に覚えのない場合は、このメールを破棄してください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                toMailAddress,
                siteURL + "/mail/confirm/" + token.toString(),
                siteURL,
                fromMailAddress
        );
        return new Mail(fromMailAddress, toMailAddress, subject, content);
    }

    /**
     * パスワード編集通知メールを生成する
     */
    public static Mail passwordEditedNoticeMail(
            final String siteURL,
            final String fromMailAddress,
            final String toMailAddress
    ) {
        final String subject = "【たびすけっち】パスワードが変更されました";
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
                siteURL,
                fromMailAddress
        );
        return new Mail(fromMailAddress, toMailAddress, subject, content);
    }

    /**
     * パスワードリセットメールを生成する
     */
    public static Mail passwordResetMail(
            final String siteURL,
            final String fromMailAddress,
            final String toMailAddress,
            final UUID token
    ) {
        final String subject = "【たびすけっち】パスワードの再設定をお願いします";
        final String content = String.format("""
                        たびすけっちをご利用いただき、ありがとうございます。
                        パスワードリセットを受け付けました。
                        
                        **以下URLをクリックして、パスワードの再設定をしてください。**
                        %s
                        
                        身に覚えのない場合は、このメールを破棄してください。
                        
                        ※このメールはシステムにより自動送信されました。
                        
                        たびすけっち
                        %s
                        お問い合わせ
                        %s
                        """,
                siteURL + "/password-reset/reset/" + token.toString(),
                siteURL,
                fromMailAddress
        );
        return new Mail(fromMailAddress, toMailAddress, subject, content);
    }
}
