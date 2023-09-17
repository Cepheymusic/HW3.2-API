select s.name, s.age, f.name
from student s left join faculty f on s.faculty_id = f.id;
select s.name a.data
from student s inner join avatar a on s.id = a.student_id