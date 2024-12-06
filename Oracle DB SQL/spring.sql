-- user: spring, password: 1234

drop table tbl_notice_file;
drop table tbl_notice;
drop table tbl_member;

drop sequence seq_notice;
drop sequence seq_notice_file;

create table tbl_member
(
    member_id varchar2(10) primary key,
    member_pw varchar2(30) not null,
    member_name varchar2(30) not null,
    member_phone char(13) not null,
    member_age number,
    member_gender char(1) check(member_gender in ('M', 'W')) not null,
    enroll_date date not null
);

insert into tbl_member values ('admin', '1234', '김찬희', '010-8645-5542', 26, 'M', sysdate);
insert into tbl_member values ('daniel', '1234', '대니엘', '010-8645-5542', 26, 'M', sysdate);
insert into tbl_member values ('mike', '1234', '마이크', '010-1111-1111', 80, 'M', sysdate);
insert into tbl_member values ('john', '1234', '존', '010-2222-2222', 90, 'M', sysdate);
insert into tbl_member values ('k', '1234', '케이', '010-3333-3333', 40, 'M', sysdate);
insert into tbl_member values ('o', '1234', '오', '010-4444-4444', 50, 'W', sysdate);

create table tbl_notice (
    notice_no number primary key,
    notice_title varchar2(200) not null,
    notice_content varchar2(2000) not null,
    notice_writer varchar2(30) references tbl_member(member_id) on delete cascade,
    notice_date date not null
);

create sequence seq_notice;

create table tbl_notice_file (
    file_no number primary key,
    notice_no number references tbl_notice(notice_no) on delete cascade,
    file_name varchar2(100) not null,
    file_path varchar2(100) not null
);

create sequence seq_notice_file;

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'admin', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'admin', sysdate);

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'daniel', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'daniel', sysdate);

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'mike', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'mike', sysdate);

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'john', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'john', sysdate);

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'k', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'k', sysdate);

insert into tbl_notice values (seq_notice.nextval, '제목0', '내용0', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목1', '내용1', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목2', '내용2', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목3', '내용3', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목4', '내용4', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목5', '내용5', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목6', '내용6', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목7', '내용7', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목8', '내용8', 'o', sysdate);
insert into tbl_notice values (seq_notice.nextval, '제목9', '내용9', 'o', sysdate);

select * from tbl_member;
select * from tbl_notice;
select * from tbl_notice_file;

select * from 
(
select rownum rnum, a.* 
    from 
    (
    select a.*
        from tbl_notice a
        order by notice_no desc
    ) a
) a
where rnum between 1 and 10;

commit;
