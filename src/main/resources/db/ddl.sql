insert INTO organization (code, name, created, last_updated, version)
values ('sol', 'Some organization ltd', now(), now(), 0);
insert into tenant (created, last_updated, version, name, tenant_id, organization_id)
values(now(), now(), 0, 'tenant-1', 't1', 1);
insert INTO app_user (created, last_updated, version, name, user_name, password_hash, tenant_id)
values(now(), now(), 0, 'Emp 1', 'emp1', 'abc', 1);