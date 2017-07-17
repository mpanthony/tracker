DROP SCHEMA IF EXISTS tracker;
CREATE SCHEMA tracker;

USE tracker;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT UNIQUE (`email`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `candidate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `active` tinyint(4) NOT NULL,
  `resume_file_name` VARCHAR(255) NULL,
  `resume_content` LONGBLOB NULL,
  `resume_content_type` VARCHAR(255) NULL,

  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `interview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `candidate_id` int(11) NOT NULL,
  `scheduled_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FOREIGN KEY(`candidate_id`) REFERENCES `candidate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `interview_participant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interview_id` int(11) NOT NULL,
  `participant_id` int(11) NOT NULL,
  `organizer` tinyint(1) DEFAULT FALSE,
  `feedback` TEXT DEFAULT NULL,
  PRIMARY KEY (`id`),

  CONSTRAINT FOREIGN KEY(`interview_id`) REFERENCES `interview` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FOREIGN KEY(`participant_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UNIQUE (`interview_id`, `participant_id`)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Insert some data
SET @peter = 1;
SET @richard = 2;
SET @russ = 3;
SET @gavin = 4;
SET @gilfoyle = 5;

SET @erlich = 1;
SET @nelson = 2;
SET @dinesh = 3;
SET @jian = 4;
SET @mike = 5;

-- Create users
INSERT INTO user (first_name,last_name,email) values ('Peter', 'Gregory', 'pgregory@boa.com');
INSERT INTO user (first_name,last_name,email) values ('Richard', 'Hendricks', 'rhendicks@boa.com');
INSERT INTO user (first_name,last_name,email) values ('Russ', 'Hanneman', 'russ@internetradio.com');
INSERT INTO user (first_name,last_name,email) values ('Gavin', 'Belson', 'gavin@hooli.com');
INSERT INTO user (first_name,last_name,email) values ('Bertram', 'Gilfoyle', 'gilfoyle@boa.com');

-- Create the candidates
INSERT INTO candidate (first_name, last_name, email, active) values ('Erlich', 'Bachman', 'ebachman@piper.com', true);
INSERT INTO candidate (first_name, last_name, email, active) values ('Nelson', 'Bighetti', 'bighead@hooli.com', true);
INSERT INTO candidate (first_name, last_name, email, active) values ('Dinesh', 'Chugtai', 'dinesh@piper.com', true);
INSERT INTO candidate (first_name, last_name, email, active) values ('Jian', 'Yang', 'j.yian@octo.com', false);
INSERT INTO candidate (first_name, last_name, email, active) values ('Mike', 'Anthony', 'manthony.mqt@gmail.com', true);

-- Create the interviews

-- Interview #1
INSERT INTO interview (candidate_id, scheduled_time) values (@nelson, '2017-06-10 18:00:00');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (1, @peter, true, 'Amazing candidate!');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (1, @richard, false, 'Meh');

-- Interview #2
INSERT INTO interview (candidate_id, scheduled_time) values (@nelson, '2017-06-15 16:00:00');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (2, @richard, true, null);

-- Interview #3
INSERT INTO interview (candidate_id, scheduled_time) values (@nelson, '2017-07-01 12:30:00');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (3, @gilfoyle, true, null);

-- Interview #4
INSERT INTO interview (candidate_id, scheduled_time) values (@mike, '2017-06-06 14:00:00');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (4, @peter, true, 'He did great, hire him!');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (4, @russ, false, 'Needs to do a coding project');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (4, @gavin, false, 'Missed this inteview, will show up for the next');

-- Interview #5
INSERT INTO interview (candidate_id, scheduled_time) values (@mike, '2017-06-12 15:00:00');
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (5, @peter, true, null);
INSERT INTO interview_participant (interview_id, participant_id, organizer, feedback) VALUES (5, @richard, false, null);
