insert into tenant(created, last_updated, version, db_password, db_url, db_user, name, tenant_id)
values(now(),now(),0,'remote','jdbc:mysql://localhost:3306/master_db','remote', 'Tenant 1', 't1');

insert INTO master_db.organization (code, name, created, last_updated, version, tenant_id)
values ('sol', 'Some organization ltd', now(), now(), 0, (SELECT LAST_INSERT_ID()));

--insert into master_db.tenant (created, last_updated, version, name, tenant_id, organization_id)
--values(now(), now(), 0, 'tenant-1', 't1', 1);
--insert INTO auth.app_user (created, last_updated, version, name, user_name, password_hash, tenant_id)
--values(now(), now(), 0, 'Emp 1', 'emp1', 'abc', 1);

insert into employee(id, created, last_updated, version, email, name, phone_number, status, organization_id)
values('root', now(), now(), 0, 'root@mail.com', 'root', '01912199929', 'ACTIVE', (SELECT LAST_INSERT_ID()));

INSERT into tenant_permission (created, last_updated, version, role, tenant_id)
values(now(), now(), 0, "SYSTEM", 1);

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Organization create", "ORGANIZATION", "create");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Organization update", "ORGANIZATION", "update");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Organization delete", "ORGANIZATION", "delete");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Employee create", "EMPLOYEE", "create");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Employee update", "EMPLOYEE", "update");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Send Invitation To Org", "INVITATION", "create");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Change invitation status", "INVITATION", "changeStatus");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Delete Invitation", "INVITATION", "delete");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Task Create", "TASK", "create");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Task update", "TASK", "update");

insert into feature (created, last_updated, version, feature_name, module, action)
values(now(), now(), 0, "Task Delete", "TASK", "delete");