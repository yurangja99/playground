create database playground;

use playground;

create table user (
id int not null auto_increment primary key,
state char(12),
age int,
gender char(2),
name varchar(30) not null,
nickname varchar(30) not null,
password varchar(30) not null,
remark int,
profile_url text,
category char(3) not null,
survey_num int,
survey_state int,
created datetime not null default now()
);

create table topic (
id int not null auto_increment primary key,
user_id int not null,
state char(12),
title text not null,
content text not null,
thumb_url text,
modified datetime not null default now()
);

create table topic_img (
id int not null auto_increment primary key,
topic_id int not null,
url text not null
);

create table comment (
id bigint not null auto_increment primary key,
user_id int not null,
topic_id int not null,
like_cnt int not null,
content text not null,
modified datetime not null default now()
);

create table tail_comment (
id bigint not null auto_increment primary key,
user_id int not null,
comment_id bigint not null,
content text not null,
modified datetime not null default now()
);

create table survey (
id int not null auto_increment primary key,
type int not null,
number int not null,
content text not null,
modified datetime not null default now()
);

create table survey_result (
id int not null auto_increment primary key,
user_id int not null,
survey_id int not null,
result int not null,
created datetime not null default now()
);

insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("성남시", 10, "남자", "김성남", "kimsung", "kimsung", 0, null, "사용자", 1, 0);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("성남시", 11, "남자", "최성남", "choisung", "choisung", 0, null, "사용자", 1, 0);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("의왕시", 15, "남자", "박의왕", "parksung", "parksung", 1, null, "사용자", 1, 1);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("의왕시", 12, "여자", "이의왕", "leesung", "leesung", 2, null, "사용자", 1, 2);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("성남시", null, null, "공무원", "gongmu", "gongmu", null, null, "마스터", null, null);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values ("의왕시", null, null, "박공무", "parkgong", "parkgong", null, null, "마스터", null, null);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values (null, null, null, "유니세", "unicef", "unicef", null, null, "마스터", null, null);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values (null, null, null, "박돈오", "parkdon", "parkdon", null, null, "관리자", null, null);
insert into user (state, age, gender, name, nickname, password, remark, profile_url, category, survey_num, survey_state) values (null, null, null, "최관리", "choikwan", "choikwan", null, null, "관리자", null, null);


insert into topic (user_id, state, title, content, thumb_url) values (5, "성남시", "면이 먼저인가, 스프가 먼저인가?", "맛있는 라면, 면과 스프 중 어느 것부터 …", null);
insert into topic (user_id, state, title, content, thumb_url) values (6, "의왕시", "횡단보도에서 어느 쪽으로 걸어야 하나?", "우리는 횡단보도를 건널 때, 최대한 안전하게 …", null);
insert into topic (user_id, state, title, content, thumb_url) values (7, null, "야간자율학습은 필요한가?", "야간자율학습은 학생들이 자율적으로 …", null);


insert into topic_img (topic_id, url) values (1, "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
insert into topic_img (topic_id, url) values (1, "https://pbs.twimg.com/profile_images/1162296345173975042/DHZimJgv_400x400.jpg");
insert into topic_img (topic_id, url) values (3, "https://www.facebook.com/images/fb_icon_325x325.png");


insert into comment (user_id, topic_id, like_cnt, content) values (2, 1, 1, "스프 먼저 넣어야 끓는점이 상승하여 …");
insert into comment (user_id, topic_id, like_cnt, content) values (3, 1, 0, "당연히 면부터 넣어야 합니다. ");
insert into comment (user_id, topic_id, like_cnt, content) values (4, 3, 1, "야자는 그나마 교육 격차를 줄여준다고 …");
insert into comment (user_id, topic_id, like_cnt, content) values (5, 2, 0, "이 주제에 댓글 좀 달아주세요");
insert into comment (user_id, topic_id, like_cnt, content) values (9, 1, 2, "물부터 넣어야 합니다. ");


insert into tail_comment (user_id, comment_id, content) values (3, 1, "끓는점이 높아진다고 라면이 더 맛있어지지는 않습니다.");
insert into tail_comment (user_id, comment_id, content) values (1, 3, "체육이나 예술에 집중하고 싶은 학생들은 …");
insert into tail_comment (user_id, comment_id, content) values (2, 4, "마스터님 힘내세요..");
insert into tail_comment (user_id, comment_id, content) values (4, 4, "무플방지위원회에서 왔습니다.");
insert into tail_comment (user_id, comment_id, content) values (3, 5, "이게맞다.");


insert into survey (type, number, content) values (1, 1, "외향적인가요?");
insert into survey (type, number, content) values (1, 2, "배려를 잘 하는 편인가요?");
insert into survey (type, number, content) values (1, 3, "할 일을 미루는 타입인가요?");
insert into survey (type, number, content) values (2, 1, "갖고 싶은 직업이 있나요?");
insert into survey (type, number, content) values (2, 2, "확실한 꿈이 있나요?");


insert into survey_result (user_id, survey_id, result) values (3, 1, 0);
insert into survey_result (user_id, survey_id, result) values (4, 1, 5);
insert into survey_result (user_id, survey_id, result) values (4, 2, 4);