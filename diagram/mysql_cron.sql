drop function cronSecond;
delimiter $$

create function cronSecond (second varchar(255))
  RETURNS boolean
BEGIN
  DECLARE current_second VARCHAR(255);

  set current_second = second(now());
  IF (instr(second, ',') >= 1 && instr(second, '-') >= 1)  THEN
    RETURN(true);
  ELSEIF (instr(second, ',') >= 1) THEN
    RETURN(true);
  ELSEIF (instr(second, '-') >= 1) THEN
    RETURN(true);
  ELSE
    IF (second = current_second) THEN
      RETURN(true);
    ELSE
      RETURN(false);
    END IF;
  END IF;
END$$

delimiter ;

-- test
select 'a' test from dual where cronSecond('1');