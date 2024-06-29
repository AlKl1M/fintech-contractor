INSERT INTO contractor.contractor (id, parent_id, name, name_full, inn, ogrn, country, industry, org_form, create_date, create_user_id)
VALUES
    ('1', NULL, 'Contractor 1', 'Full Name 1', '111111111', '111111111', 'ABH', 1, 1, '2022-01-01 00:00:00', 'user1'),
    ('2', NULL, 'Contractor 2', 'Full Name 2', '222222222', '222222222', 'ABW', 2, 2, '2022-01-01 00:00:00', 'user2'),
    ('3', '1', 'Subcontractor 1', 'Full Sub Name 1', '333333333', '333333333', 'AFG', 1, 1, '2022-01-01 00:00:00', 'user3'),
    ('4', NULL, 'Contractor 3', 'Full Name 3', '444444444', '444444444', 'AFG', 3, 3, '2022-01-01 00:00:00', 'user4'),
    ('5', NULL, 'Contractor 4', 'Full Name 4', '555555555', '555555555', 'AFG', 4, 4, '2022-01-01 00:00:00', 'user5'),
    ('6', NULL, 'Contractor 5', 'Full Name 5', '666666666', '666666666', 'ARM', 5, 5, '2022-01-01 00:00:00', 'user6'),
    ('7', NULL, 'Contractor 6', 'Full Name 6', '777777777', '777777777', 'ASM', 6, 6, '2022-01-01 00:00:00', 'user7'),
    ('8', NULL, 'Contractor 7', 'Full Name 7', '888888888', '888888888', 'BFA', 7, 7, '2022-01-01 00:00:00', 'user8'),
    ('9', NULL, 'Contractor 8', 'Full Name 8', '999999999', '999999999', 'AFG', 8, 8, '2022-01-01 00:00:00', 'user9'),
    ('10', NULL, 'Contractor 9', 'Full Name 9', '1010101010', '1010101010', 'AFG', 9, 9, '2022-01-01 00:00:00', 'user10');