-- phpMyAdmin SQL Dump
-- version 4.1.12
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 06, 2015 at 09:59 AM
-- Server version: 5.6.16
-- PHP Version: 5.5.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `stack_exchange`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE IF NOT EXISTS `answer` (
  `id_answer` int(4) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `vote_num` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_user` int(4) NOT NULL,
  `id_question` int(11) NOT NULL,
  PRIMARY KEY (`id_answer`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

--
-- Dumping data for table `answer`
--

INSERT INTO `answer` (`id_answer`, `content`, `vote_num`, `datetime`, `id_user`, `id_question`) VALUES
(43, 'Barkside''s answer works with FragmentPagerAdapter but doesn''t work with FragmentStatePagerAdapter, because it doesn''t set tags on fragments it passes to FragmentManager.\r\n<br>\r\n<br>With FragmentStatePagerAdapter it seems we can get by, using its instantiateItem(ViewGroup container, int position) call. It returns reference to fragment at position position. If FragmentStatePagerAdapter already holds reference to fragment in question, instantiateItem just returns reference to that fragment, and doesn''t call getItem() to instantiate it again.\r\n<br>\r\n<br>So, suppose, I''m currently looking at fragment #50, and want to access fragment #49. Since they are close, there''s a good chance the #49 will be already instantiated.', 1, '2015-10-28 10:43:30', 3, 2),
(46, 'ga ngerti', 0, '2015-11-27 23:15:22', 17, 6),
(47, 'bingunggggggg', 0, '2015-11-27 23:21:03', 17, 6),
(49, 'Jelek lu', 3, '2015-11-27 23:22:40', 17, 3),
(51, '', 0, '2015-12-06 15:43:30', 2, 6),
(52, '', 0, '2015-12-06 15:44:25', 2, 6),
(53, '', 0, '2015-12-06 15:45:15', 2, 6),
(54, '', 0, '2015-12-06 15:45:52', 2, 6);

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `id_comment` int(4) NOT NULL AUTO_INCREMENT,
  `id_question` int(4) NOT NULL,
  `id_user` int(4) NOT NULL,
  `content` text NOT NULL,
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_comment`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `id_question` int(4) NOT NULL AUTO_INCREMENT,
  `topic` varchar(535) NOT NULL,
  `content` text NOT NULL,
  `vote_num` int(11) NOT NULL DEFAULT '0',
  `datetime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_user` int(4) NOT NULL,
  PRIMARY KEY (`id_question`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id_question`, `topic`, `content`, `vote_num`, `datetime`, `id_user`) VALUES
(2, 'Update data in ListFragment as part of ViewPager', 'I''m using the v4 compatibility ViewPager in Android. My FragmentActivity has a bunch of data which is to be displayed in different ways on different pages in my ViewPager. So far I just have 3 instances of the same ListFragment, but in the future I will have 3 instances of different ListFragments. The ViewPager is on a vertical phone screen, the lists are not side-by-side.\r<br>\r<br>Now a button on the ListFragment starts an separate full-page activity (via the FragmentActivty), which returns and FragentActivity modifies the data, saves it, then attempts to update all views in its ViewPager. It is here I am stuck.', 4, '2015-10-28 10:41:32', 2),
(3, 'Jangan dibuka', 'Apakah saya ganteng?', -1, '2015-11-27 20:10:58', 6),
(4, 'test', 'TestsLA', 0, '2015-11-27 21:25:41', 2),
(5, 'Konsep melakukan sharing session dengan menggunakan REST', 'Agar suatu web application bersifat RESTful, Session disimpan pada client. Setiap kali server melakukan operasi tertentu, server harus memeriksa session saat ini. Karena session tersebut terdapat pada client, server harus melakukan request kepada client apakah session saat ini telah habis atau belum. Oleh karena itu, session management dapat dilakukan secara merata oleh seluruh client (client menyimpan session dan server dapat melakukan request secara stateless).\r\n', 0, '2015-11-27 22:32:12', 2),
(6, 'How do I disable/enable a form element?', 'Examples of where is comes up\r\n\r\nHTTP request processing\r\n\r\nContext objects in the form of request attributes are often used: see expressjs, Java Servlets or .net''s owin.\r\n\r\nLogging\r\n\r\nFor Java logging folk often use globals / singletons. See the typical log4j / commons logging / java logging patterns.\r\n\r\nTransactions\r\n\r\nThread locals are often used to keep a transaction or session associated with a chain of method calls to avoid needing to pass them as parameters to all the methods that don''t need them.', 1, '2015-11-27 22:33:11', 2),
(8, 'Nanan', 'lala', 0, '2015-11-30 11:27:23', 20);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(535) NOT NULL,
  `email` varchar(535) NOT NULL,
  `password` varchar(128) NOT NULL,
  `token` varchar(256) DEFAULT NULL,
  `lifetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=22 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `name`, `email`, `password`, `token`, `lifetime`) VALUES
(2, 'Irene Wiliudarsan', 'irene@gmail.com', 'test', 'dc62224c-49f3-3a61-b59f-c99f91093b89#Firefox 42#0:0:0:0:0:0:0:1', '2015-12-06 08:52:20'),
(3, 'Jen Teda', 'jenteda@hotmail.com', 'jenteda11', '6ee2fc36-0111-39ab-8947-b15e1aa26534#Firefox 42#0:0:0:0:0:0:0:1', '2015-12-06 07:49:57'),
(4, 'Abraham Lincoln', 'abraham.lincoln@live.com', 'lincolnkece', NULL, '2015-12-05 11:57:30'),
(5, 'Lingga', 'lingga@yahoo.com', 'okelahkalobegitu9002', NULL, '2015-12-05 11:57:45'),
(7, 'Angela Lynn', 'angela@gmail.com', 'test123', NULL, '2015-11-23 09:13:32'),
(8, 'Devina Ekawati', 'dvina.deph@gmail.com', 'devina', '2cec34f6-df22-384d-b37a-7c2b4a59c0bf', '2015-11-26 13:47:09'),
(9, 'Harry Potter', 'harry@gmail.com', 'harry', NULL, '2015-11-23 09:56:16'),
(11, 'Ron Weasley', 'ron@gmail.com', 'ron', NULL, '2015-11-23 10:28:51'),
(12, 'William Sentosa', 'william@gmail.com', 'test', NULL, '2015-11-23 10:41:34'),
(13, 'Candy Olivia', 'candy@gmail.com', 'candy', NULL, '2015-11-23 10:55:34'),
(15, 'Hermonie Granger', 'hermonie@gmail.com', 'hermonie', NULL, '2015-11-23 11:02:34'),
(17, 'Angela Lynn', '13513032@std.stei.itb.ac.id', 'blabla', '8e81ca6b-d052-3c12-9bfc-a92b6a8f4b83', '2015-11-27 16:30:39'),
(19, 'Pak Riza', 'riza@informatika.org', 'test', 'a15a94d5-0f85-3a72-878b-ba9c1512c7ec', '2015-11-30 04:16:59'),
(20, 'Pak Yudis', 'yudis@informatika.org', 'test2', '1fad1876-20dd-31b1-bb0a-7d8049cdc31c', '2015-11-30 04:28:09'),
(21, 'Test', 'test@gmail.com', 'test', '91f9e8a8-470e-3d71-b583-fa7e472d8181#Chrome 46#0:0:0:0:0:0:0:1', '2015-12-06 07:09:59');

-- --------------------------------------------------------

--
-- Table structure for table `vote`
--

CREATE TABLE IF NOT EXISTS `vote` (
  `id_user` int(4) NOT NULL,
  `id_question` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vote`
--

INSERT INTO `vote` (`id_user`, `id_question`) VALUES
(17, 6),
(17, 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
