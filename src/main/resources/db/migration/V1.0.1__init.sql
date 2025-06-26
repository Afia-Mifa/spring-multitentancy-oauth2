insert INTO master_db.organization (code, name, created, last_updated, version)
values ('sol', 'Some organization ltd', now(), now(), 0);
--insert into master_db.tenant (created, last_updated, version, name, tenant_id, organization_id)
--values(now(), now(), 0, 'tenant-1', 't1', 1);
--insert INTO auth.app_user (created, last_updated, version, name, user_name, password_hash, tenant_id)
--values(now(), now(), 0, 'Emp 1', 'emp1', 'abc', 1);

insert into employee(id, created, last_updated, version, email, name, phone_number, status, organization_id)
values('root', now(), now(), 0, 'root@mail.com', 'root', '01912199929', 'ACTIVE', 3);

insert into tenant(created, last_updated, version, db_password, db_url, db_user, name, tenant_id, organization_id)
values(now(),now(),0,'remote','jdbc:mysql://localhost:3306/master_db','remote', 'Tenant 1', 't1',3);