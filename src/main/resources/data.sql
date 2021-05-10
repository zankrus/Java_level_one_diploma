INSERT INTO projects(id,name) values ( 1, 'Тестовый проект' ),( 2, 'Второй проект' );

INSERT INTO suites(id, name, project_id) values ( 1,'Проверка авторизации','1'),( 2,'Проверка оплаты','2');

INSERT INTO cases(id, suite_id, description, attached_files) VALUES ( 1, 1, 'ВВодим логин', 'картинка.жпг' )
