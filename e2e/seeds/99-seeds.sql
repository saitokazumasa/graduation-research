-- メール認証済みのテスト用ユーザーを追加
-- mail_address: testuser@example.com
-- password: testuser
INSERT INTO public.users (id, mail_address, password, mail_address_authenticated) VALUES (1, 'testuser@example.com', '$2a$10$ROxsNdyPajVVcaJgcP07.e3ipaty122LyYIXHx0IYdcVZRShmOKeO', true);
