-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 07, 2017 at 12:49 PM
-- Server version: 5.7.19
-- PHP Version: 7.0.18-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `informatics`
--

-- --------------------------------------------------------

--
-- Table structure for table `Address`
--

CREATE TABLE `Address` (
  `ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `port` int(11) NOT NULL,
  `inetAddress` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Address`
--

INSERT INTO `Address` (`ID`, `user_id`, `port`, `inetAddress`) VALUES
(1, 303346716, -1, ''),
(2, 303346727, -1, ''),
(3, 303346733, -1, ''),
(3, 303346734, -1, ''),
(4, 303346735, -1, ''),
(5, 303346736, -1, ''),
(6, 303346737, -1, ''),
(7, 303346738, -1, ''),
(8, 303346739, -1, ''),
(9, 303346740, -1, ''),
(10, 303346741, -1, ''),
(11, 303346742, -1, '');

-- --------------------------------------------------------

--
-- Table structure for table `ApplicationLog`
--

CREATE TABLE `ApplicationLog` (
  `_ID` int(11) NOT NULL,
  `_Message` varchar(500) CHARACTER SET utf32 COLLATE utf32_unicode_520_ci NOT NULL,
  `_timestamp` bigint(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `broadcast_list`
--

CREATE TABLE `broadcast_list` (
  `id` int(11) NOT NULL,
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  `user_ids` text COLLATE utf8_unicode_ci,
  `post_timestamp` bigint(15) UNSIGNED NOT NULL,
  `broadcast_user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chats`
--

CREATE TABLE `chats` (
  `id` int(11) UNSIGNED NOT NULL,
  `user1_id` int(11) UNSIGNED NOT NULL,
  `user2_id` int(11) UNSIGNED NOT NULL,
  `start_timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `chats`
--

INSERT INTO `chats` (`id`, `user1_id`, `user2_id`, `start_timestamp`) VALUES
(52, 303346780, 303346740, 1504559991073),
(53, 303346781, 303346740, 1504593043202),
(54, 303346727, 303346740, 1504599414501),
(55, 303346782, 303346740, 1504606029850),
(56, 303346737, 303346740, 1504610378501),
(57, 303346783, 303346740, 1504614215202),
(58, 303346736, 303346737, 1504982831123),
(59, 303346765, 303346733, 1505070047556),
(60, 303346740, 303346733, 1505305559881),
(61, 303346733, 303346737, 1506175993346),
(62, 303346733, 303346736, 1506176688067),
(63, 303346727, 303346733, 1506257977612);

-- --------------------------------------------------------

--
-- Table structure for table `distances`
--

CREATE TABLE `distances` (
  `id` int(10) UNSIGNED NOT NULL,
  `distance` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `distances`
--

INSERT INTO `distances` (`id`, `distance`) VALUES
(1, 5),
(2, 10),
(3, 20),
(4, 50),
(5, 75),
(6, 100);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `id` int(11) NOT NULL,
  `events_types_id` int(11) NOT NULL,
  `events_visibilitys_id` int(11) NOT NULL,
  `host_user_id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `date` bigint(20) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `duration` int(11) NOT NULL,
  `createdTimestamp` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `events_types_id`, `events_visibilitys_id`, `host_user_id`, `title`, `date`, `description`, `duration`, `createdTimestamp`) VALUES
(11, 0, 0, 303346740, 'tfvtvttvbygbybtvyv', 1506612300740, 'gvfcf gvfchbfcbgg. g. g g f f', 15, 1506180315959),
(19, 0, 0, 303346733, 'Test Event #5', 1506262500746, 'testing 123', 60, 1506251738620),
(20, 3333, 1, 20000, 'Hey you got it', 12312312, 'The best', 213123, 1506252220654),
(21, 5, 1, 303346727, 'Its starting', 1506484800577, 'We\'re recruiting for the next shushter movie', 480, 1506271481223),
(22, 1, 1, 303346733, 'Testing #1', 1506098645254, 'Need to get events back set up', 60, 1506278740018),
(23, 1, 1, 303346727, 'Testing #5', 1506098645254, 'Another one', 60, 1506278780595),
(24, 1, 1, 303346727, 'Testing #5', 1506098645254, 'Another one', 60, 1506282554177),
(25, 1, 1, 303346727, 'Testing #5', 1506098645254, 'Another one', 60, 1506282594327),
(26, 1, 1, 303346727, 'Testing #5', 1506098645254, 'Another one', 60, 1506282716518),
(27, 1, 0, 303346727, 'Fish net ', 1506456420998, 'Just testing some things', 15, 1506283673777),
(28, 0, 0, 303346727, 'Testing #6', 1506457260606, 'ifwnfajnfnwfnwnjk', 15, 1506284519632),
(29, 1, 1, 303346740, 'Event Invite ', 1506484800478, 'Testing responses to event invite', 120, 1506371754717),
(30, 4, 0, 303346740, 'Testing #7', 1506758340726, 'Testing Event Invites and Responses', 15, 1506499231842),
(31, 0, 0, 303346740, 'Another test #8', 1506712260878, 'Testing invites', 15, 1506625932185),
(32, 0, 0, 303346727, 'Event #9', 1506802860033, 'Testing another one...', 15, 1506630131687),
(33, 0, 0, 303346740, '#9Event ', 1506690000849, 'getting tired of this ', 15, 1506672374251),
(34, 0, 0, 303346740, '#10 Event', 1506927600978, 'going for the kill', 15, 1506786558598),
(35, 5, 0, 303346737, 'Bob\'s Test Event', 1506843300414, 'this is a test event', 240, 1506796584726),
(36, 5, 0, 303346736, 'John\'s Testing Event', 1506844080125, 'welcome to my event!!', 480, 1506797336842),
(37, 4, 0, 303346727, '#11 Testing', 1506852000698, 'jffgjyfgjngcjgffghchcgch', 15, 1506805117922),
(38, 0, 0, 303346727, 'Event #12 i think', 1506880800566, 'Going on home', 15, 1506866848698),
(39, 0, 0, 303346727, '#13 Event ...', 1507113000502, 'So getting tired of this', 15, 1506931786279),
(40, 0, 0, 303346740, '#14 Event', 1507111200460, 'I will be there at least', 15, 1506932162292),
(41, 5, 0, 303346736, 'the jc event', 1506987660805, 'testing 123', 480, 1506933673820),
(42, 0, 0, 303346740, '#15 Event', 1507106640528, 'Testing .......', 15, 1506933866137),
(43, 0, 0, 303346740, '#16 Event ', 1507199400027, 'Testing things ', 15, 1507110822252),
(44, 0, 0, 303346727, '#17 Event', 1507374000891, 'Event Testing ...', 15, 1507114905471),
(45, 4, 0, 303346736, 'john\'s super test event', 1507205040576, 'testing 123', 240, 1507115090310),
(46, 0, 0, 303346740, '#18 Event...', 1507132800386, 'eish ...................', 15, 1507118639251),
(47, 4, 0, 303346733, 'ron\'s test event', 1507371960612, 'testing 123', 120, 1507192020324),
(48, 0, 0, 303346727, 'Event #20', 1507231800890, 'Checking attendees ...', 240, 1507227818482),
(49, 0, 0, 303346727, 'Event #21', 1507231800595, 'Checking event attendees second round', 15, 1507228436695),
(50, 0, 0, 303346716, '#22 Event', 1507289340682, 'Checking requests', 15, 1507278604788),
(51, 0, 0, 303346727, '#23 Event', 1507289400266, 'event testing .....', 15, 1507286123870);

-- --------------------------------------------------------

--
-- Table structure for table `events_attendees`
--

CREATE TABLE `events_attendees` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `events_id` int(11) NOT NULL,
  `attended` tinyint(1) NOT NULL,
  `skills_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_attendees`
--

INSERT INTO `events_attendees` (`id`, `user_id`, `events_id`, `attended`, `skills_id`) VALUES
(3, 303346727, 30, 1, 0),
(5, 303346733, 29, 1, 0),
(6, 303346727, 31, 0, 0),
(7, 303346727, 34, 0, 0),
(8, 303346740, 39, 0, 0),
(9, 303346727, 40, 0, 0),
(10, 303346727, 42, 0, 0),
(11, 303346727, 42, 0, 0),
(12, 303346727, 46, 0, 1),
(13, 303346740, 48, 1, 9),
(14, 303346716, 48, 0, 8),
(15, 303346740, 49, 0, 9),
(16, 303346716, 49, 1, 8),
(17, 303346736, 48, 1, 8),
(18, 303346736, 49, 1, 8),
(19, 303346737, 48, 1, 9),
(20, 303346737, 49, 0, 9),
(21, 303346736, 51, 0, 8),
(22, 303346736, 51, 0, 8);

-- --------------------------------------------------------

--
-- Table structure for table `events_invites`
--

CREATE TABLE `events_invites` (
  `id` int(11) NOT NULL,
  `receiver_user_id` int(11) NOT NULL,
  `events_responses_id` int(11) NOT NULL,
  `events_id` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  `reply` varchar(255) NOT NULL,
  `skills_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_invites`
--

INSERT INTO `events_invites` (`id`, `receiver_user_id`, `events_responses_id`, `events_id`, `message`, `reply`, `skills_id`) VALUES
(1, 303346740, 2, 21, 'Would you like you to join my event?', 'null', 0),
(2, 303346737, 2, 21, 'You have been invited to attend an Event by mrBob', 'null', 0),
(3, 303346736, 2, 21, 'You have been invited to attend an Event by theJC', 'null', 0),
(4, 303346735, 2, 21, 'You have been invited to attend an Event by adminMarc', 'null', 0),
(5, 303346734, 2, 21, 'You have been invited to attend an Event by AdminExample', 'null', 0),
(6, 303346716, 2, 21, 'You have been invited to attend an Event by dv', 'null', 0),
(7, 303346733, 2, 21, 'You have been invited to attend an Event by dv', 'null', 0),
(8, 303346727, 2, 29, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(9, 303346733, 1, 29, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(10, 303346727, 1, 30, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(11, 303346727, 1, 31, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(12, 303346727, 2, 33, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(13, 303346727, 2, 33, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(14, -1, 2, 32, 'Please join', 'null', 0),
(15, -1, 2, 32, 'You have been invited to my Event', 'null', 0),
(16, 303346727, 1, 34, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(17, 303346736, 2, 35, 'hey john wanna join my event? regards bob', 'null', 0),
(18, 303346737, 2, 36, 'yo bob join me bruh', 'null', 0),
(19, 303346736, 2, 35, 'You have been invited to attend an Event by mrBob', 'null', 0),
(20, 303346733, 2, 35, 'You have been invited to attend an Event by mrBob', 'null', 0),
(21, 303346733, 2, 36, 'yo yo join my event!!!!!', 'null', 0),
(22, 303346733, 2, 36, 'i can send another invite? can i spam???', 'null', 0),
(23, 303346740, 2, 38, 'You have been invited to attend an Event by dv', 'null', 13),
(24, 303346733, 2, 38, 'You have been invited to attend an Event by dv', 'null', 13),
(25, 303346740, 1, 39, 'You have been invited to attend an Event by dv', 'null', 6),
(26, 303346727, 1, 40, 'You have been invited to attend an Event by Vanneh', 'null', 9),
(27, 303346740, 2, 41, 'You have been invited to attend an Event by theJC', 'null', 12),
(28, 303346733, 2, 41, 'You have been invited to attend an Event by theJC', 'null', 12),
(29, 303346716, 2, 41, 'You have been invited to attend an Event by theJC', 'null', 12),
(30, 303346727, 1, 42, 'You have been invited to attend an Event by Vanneh', 'null', 12),
(31, 303346727, 1, 42, 'You have been invited to attend an Event by Vanneh', 'null', 0),
(32, 303346727, 2, 42, 'Would you like to join my event bro?', 'null', 0),
(33, 303346733, 2, 40, 'You have been invited to attend an Event by Vanneh', 'null', 13),
(34, 303346736, 2, 40, 'You have been invited to attend an Event by Vanneh', 'null', 12),
(35, 303346727, 3, 43, 'You have been invited to attend an Event by Vanneh', 'null', 13),
(36, 303346737, 2, 39, 'Please Join my event', 'null', 303346727),
(37, 303346736, 2, 44, 'You wanna join bro?', 'null', 303346727),
(38, 303346740, 2, 45, 'You have been invited to attend an Event by theJC', 'null', 7),
(39, 303346737, 2, 45, 'You have been invited to attend an Event by theJC', 'null', 7),
(40, 303346782, 2, 45, 'hey this is john, wanna join?', 'null', 303346736),
(41, 303346765, 2, 45, 'hey zing!!! ', 'null', 303346736),
(42, 303346727, 3, 44, 'Wanna join?', 'null', 5),
(43, 303346727, 3, 44, 'Wanna join?', 'null', 5),
(44, 303346716, 2, 44, 'Wanna join bro?', 'null', 0),
(45, 303346740, 2, 44, 'Wanna join bro?', 'null', 0),
(46, 303346727, 1, 46, 'You have been invited to attend an Event by Vanneh', 'null', 4),
(47, 303346733, 2, 46, 'You have been invited to attend an Event by Vanneh', 'null', 4),
(48, 303346736, 2, 46, 'You have been invited to attend an Event by Vanneh', 'null', 1),
(49, 303346716, 2, 46, 'You have been invited to attend an Event by Vanneh', 'null', 1),
(50, 303346737, 1, 48, 'You have been invited to attend an Event by dv', 'null', 9),
(51, 303346733, 2, 48, 'You have been invited to attend an Event by dv', 'null', 9),
(52, 303346740, 1, 48, 'You have been invited to attend an Event by dv', 'null', 9),
(53, 303346736, 1, 48, 'You have been invited to attend an Event by dv', 'null', 8),
(54, 303346716, 1, 48, 'You have been invited to attend an Event by dv', 'null', 8),
(55, 303346736, 1, 49, 'You have been invited to attend an Event by dv', 'null', 8),
(56, 303346716, 1, 49, 'You have been invited to attend an Event by dv', 'null', 8),
(57, 303346740, 1, 49, 'You have been invited to attend an Event by dv', 'null', 9),
(58, 303346733, 2, 49, 'You have been invited to attend an Event by dv', 'null', 9),
(59, 303346737, 1, 49, 'You have been invited to attend an Event by dv', 'null', 9),
(60, 303346740, 2, 51, 'You have been invited to attend an Event by dv', 'null', 8),
(61, 303346736, 1, 51, 'You have been invited to attend an Event by dv', 'null', 8);

-- --------------------------------------------------------

--
-- Table structure for table `events_locations`
--

CREATE TABLE `events_locations` (
  `id` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `address` varchar(255) NOT NULL,
  `events_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_locations`
--

INSERT INTO `events_locations` (`id`, `latitude`, `longitude`, `address`, `events_id`) VALUES
(2, -26.32699342293217, 27.85215647891164, 'Protea Ave, Lenasia, 1827, South Africa', 11),
(5, -14.497726505442998, 29.484685175120823, 'Lenasia, 1821, South Africa', 39),
(8, -26.3255167526181, 27.8668955527246, 'Cnr Nirvana Dr, Lenasia, 1821, South Africa', 42),
(9, -26.185147157052032, 28.000858817249537, 'Kingsway and University Rds, Auckland Park, Rossmore, Johannesburg, 2092, South Africa', 40),
(10, -26.18582440821733, 28.000858817249537, 'Klipriviersoog, Soweto, 1811, South Africa', 44),
(12, -26.18480537087338, 27.997507732361555, 'Klipriviersoog, Soweto, 1811, South Africa', 43),
(13, -26.31560871448204, 27.829051595181227, 'Signet Terrace Shopping Centre, 82 Gemsbok St, Lenasia, Johannesburg, 1827, South Africa', 46),
(14, -26.19009150655974, 27.98844488337636, 'Studente Ave, Rossmore, Johannesburg, 2092, South Africa', 48),
(15, -26.195214952919297, 27.999776881188154, 'Shop 47 High Street, Mayfair, Mayfair West, Johannesburg, 2092, South Africa', 49),
(16, -26.315259823983894, 27.831217478960752, '12 Robin Ave, Lenasia, 1821, South Africa', 50),
(17, -26.180839401940133, 28.01731651648879, '11 Guild Rd, Parktown, Johannesburg, 2193, South Africa', 51);

-- --------------------------------------------------------

--
-- Table structure for table `events_requests`
--

CREATE TABLE `events_requests` (
  `id` int(10) UNSIGNED NOT NULL,
  `skills_id` int(11) NOT NULL,
  `sender_user_id` int(11) NOT NULL,
  `events_responses_id` int(11) NOT NULL,
  `events_id` int(11) NOT NULL,
  `message` varchar(255) NOT NULL,
  `reply` varchar(255) NOT NULL,
  `timestamp` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_requests`
--

INSERT INTO `events_requests` (`id`, `skills_id`, `sender_user_id`, `events_responses_id`, `events_id`, `message`, `reply`, `timestamp`) VALUES
(1, 1111, 1111, 2, 1111, 'asdsad', 'asdasdsa', 1506947757721),
(2, 12, 303346716, 2, 42, 'Can i join the event Please?', 'nothing', 1506962052455),
(3, 13, 303346727, 1, 47, 'Can I join please?', 'nothing', 1507196835723);

-- --------------------------------------------------------

--
-- Table structure for table `events_responses`
--

CREATE TABLE `events_responses` (
  `id` int(11) NOT NULL,
  `response` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_responses`
--

INSERT INTO `events_responses` (`id`, `response`) VALUES
(1, 'Accepted'),
(2, 'Pending'),
(3, 'Declined');

-- --------------------------------------------------------

--
-- Table structure for table `events_reviews`
--

CREATE TABLE `events_reviews` (
  `id` int(10) UNSIGNED NOT NULL,
  `reviewer_user_id` int(10) UNSIGNED NOT NULL,
  `reviewee_user_id` int(10) UNSIGNED NOT NULL,
  `events_id` int(10) UNSIGNED NOT NULL,
  `skill_id` int(10) UNSIGNED NOT NULL,
  `rating` int(11) NOT NULL DEFAULT '-1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_reviews`
--

INSERT INTO `events_reviews` (`id`, `reviewer_user_id`, `reviewee_user_id`, `events_id`, `skill_id`, `rating`) VALUES
(3, 0, 100, 19, 0, -1),
(4, 0, 200, 19, 0, -1),
(5, 100, 0, 19, 0, 4),
(6, 100, 200, 19, 0, -1),
(7, 200, 0, 19, 0, -1),
(8, 200, 100, 19, 0, -1),
(35, 303346733, 303346736, 19, 3, 2),
(36, 303346733, 303346737, 19, 9, -1),
(37, 303346736, 303346733, 19, 1, 4),
(38, 303346736, 303346737, 19, 9, -1),
(39, 303346737, 303346733, 19, 1, 3),
(40, 303346737, 303346736, 19, 3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `events_skills`
--

CREATE TABLE `events_skills` (
  `id` int(10) UNSIGNED NOT NULL,
  `events_id` int(10) UNSIGNED NOT NULL,
  `skills_id` int(10) UNSIGNED NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_skills`
--

INSERT INTO `events_skills` (`id`, `events_id`, `skills_id`, `status`) VALUES
(1, 20, 1, 0),
(2, 20, 2, 0),
(3, 20, 3, 0),
(4, 25, 1, 0),
(5, 26, 1, 0),
(6, 26, 2, 0),
(7, 28, 12, 0),
(8, 28, 23, 0),
(9, 29, 8, 0),
(10, 29, 0, 0),
(11, 30, 8, 0),
(12, 30, 9, 0),
(13, 32, 8, 0),
(14, 32, 11, 0),
(15, 33, 23, 0),
(16, 33, 9, 0),
(17, 34, 10, 0),
(18, 34, 2, 0),
(19, 35, 7, 0),
(20, 35, 0, 0),
(21, 35, 9, 0),
(22, 35, 13, 0),
(23, 36, 8, 0),
(24, 36, 2, 0),
(25, 36, 12, 0),
(26, 36, 13, 0),
(27, 37, 6, 0),
(28, 37, 7, 0),
(29, 38, 13, 0),
(30, 38, 23, 0),
(31, 39, 6, 0),
(32, 39, 8, 0),
(33, 40, 2, 0),
(34, 40, 9, 0),
(35, 40, 13, 0),
(36, 40, 12, 0),
(37, 41, 8, 0),
(38, 41, 12, 0),
(39, 41, 9, 0),
(40, 41, 13, 0),
(41, 41, 0, 0),
(42, 42, 12, 0),
(43, 42, 0, 0),
(44, 43, 8, 0),
(45, 43, 13, 0),
(46, 44, 8, 0),
(47, 44, 5, 0),
(48, 44, 0, 0),
(49, 45, 7, 0),
(50, 45, 0, 0),
(51, 45, 9, 0),
(52, 45, 13, 0),
(53, 46, 6, 0),
(54, 46, 10, 0),
(55, 46, 4, 0),
(56, 46, 1, 0),
(57, 47, 7, 0),
(58, 47, 0, 0),
(59, 47, 9, 0),
(60, 47, 13, 0),
(61, 47, 12, 0),
(62, 48, 9, 0),
(63, 48, 13, 0),
(64, 48, 12, 0),
(65, 48, 8, 0),
(66, 48, 5, 0),
(67, 49, 8, 0),
(68, 49, 9, 0),
(69, 50, 9, 0),
(70, 50, 8, 0),
(71, 50, 0, 0),
(72, 50, 7, 0),
(73, 51, 9, 0),
(74, 51, 8, 0),
(75, 51, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `events_types`
--

CREATE TABLE `events_types` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_types`
--

INSERT INTO `events_types` (`id`, `description`) VALUES
(0, 'General'),
(1, 'Meet and Greet'),
(2, 'Try-out'),
(3, 'Audition'),
(4, 'Promotional'),
(5, 'Recruitment'),
(6, 'Movement');

-- --------------------------------------------------------

--
-- Table structure for table `events_visibilitys`
--

CREATE TABLE `events_visibilitys` (
  `id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_visibilitys`
--

INSERT INTO `events_visibilitys` (`id`, `description`) VALUES
(0, 'public'),
(1, 'private'),
(2, 'followers');

-- --------------------------------------------------------

--
-- Table structure for table `followers`
--

CREATE TABLE `followers` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  `liked_id` int(11) UNSIGNED NOT NULL,
  `timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `followers`
--

INSERT INTO `followers` (`id`, `user_id`, `liked_id`, `timestamp`) VALUES
(1, 303346727, 303346716, 1501436933320),
(2, 303346716, 303346727, 1501437248257),
(7, 303346727, 303346733, 1501786562876),
(8, 303346716, 303346733, 1501786579268),
(9, 303346733, 303346716, 1501786596878),
(10, 303346733, 303346727, 1501786607592),
(11, 303346733, 303346737, 1502038410207),
(12, 303346737, 303346733, 1502038429800),
(16, 303346727, 303346734, 1502040998382),
(17, 303346727, 303346735, 1502041004851),
(18, 303346727, 303346736, 1502041008815),
(19, 303346727, 303346737, 1502041011875),
(30, 303346733, 303346740, 1502136882739),
(31, 303346727, 303346738, 1502138861137),
(32, 303346740, 303346736, 1502139431985),
(36, 303346740, 303346733, 1502173144746),
(39, 303346740, 303346727, 1502174858708),
(56, 303346740, 303346716, 1503126422104),
(57, 303346740, 303346737, 1503126531173),
(85, 303346736, 303346737, 1503170307647),
(86, 303346737, 303346736, 1503170340340),
(91, 303346727, 303346740, 1503329218257),
(94, 303346733, 303346736, 1503515660062),
(100, 303346757, 303346740, 1503776749875),
(106, 303346733, 303346778, 1504210687043),
(116, 303346736, 303346733, 1504343396073),
(121, 303346736, 303346736, 1504345813206),
(122, 303346736, 303346740, 1504345820769),
(123, 303346736, 303346716, 1504345825354),
(143, 303346779, 303346737, 1504470381220),
(144, 303346780, 303346727, 1504557689068),
(145, 303346780, 303346740, 1504557697158),
(146, 303346780, 303346733, 1504557896708),
(147, 303346740, 303346780, 1504559432981),
(148, 303346740, 303346765, 1504560078935),
(149, 303346781, 303346740, 1504593033202),
(150, 303346782, 303346740, 1504605585332),
(151, 303346782, 303346765, 1504605599350),
(154, 303346740, 303346782, 1504605704390),
(155, 303346737, 303346740, 1504609720056),
(156, 303346783, 303346765, 1504613265934),
(157, 303346740, 303346783, 1504613332406),
(158, 303346783, 303346740, 1504613902794),
(159, 303346765, 303346733, 1505070027429),
(170, 303346740, 303346758, 1505295269655),
(179, 303346716, 303346740, 1506876683871);

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `id` int(10) UNSIGNED NOT NULL,
  `user1_id` int(11) UNSIGNED NOT NULL,
  `user2_id` int(11) UNSIGNED NOT NULL,
  `timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`id`, `user1_id`, `user2_id`, `timestamp`) VALUES
(1, 303346716, 303346727, 1501437248267),
(2, 303346729, 303346716, 1501439908971),
(3, 303346733, 303346716, 1501786596897),
(4, 303346733, 303346727, 1501786607596),
(5, 303346737, 303346733, 1502038429821),
(6, 303346740, 303346740, 1502173107355),
(7, 303346740, 303346733, 1502173144764),
(8, 303346733, 303346733, 1502178754691),
(9, 303346737, 303346736, 1503170340346),
(10, 303346727, 303346740, 1503329218268),
(11, 303346736, 303346733, 1504343396080),
(12, 303346736, 303346736, 1504345813212),
(13, 303346736, 303346740, 1504345820773),
(14, 303346740, 303346780, 1504559432988),
(15, 303346740, 303346782, 1504605704400),
(16, 303346737, 303346740, 1504609720062),
(17, 303346783, 303346740, 1504613902801),
(18, 303346737, 303346779, 1506590002776),
(19, 303346737, 303346779, 1506590016172),
(20, 303346737, 303346779, 1506590318861),
(21, 303346716, 303346740, 1506876683882);

-- --------------------------------------------------------

--
-- Table structure for table `genres`
--

CREATE TABLE `genres` (
  `id` int(11) UNSIGNED NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `created_timestamp` bigint(15) UNSIGNED NOT NULL,
  `updated_timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `genres`
--

INSERT INTO `genres` (`id`, `name`, `created_timestamp`, `updated_timestamp`) VALUES
(0, 'Jazz', 72782, 72502),
(1, 'Rock', 20170420181703, 0),
(2, 'Rap', 20170420181703, 0),
(3, 'Pop', 20170420181703, 0),
(4, 'Indie', 20170420181703, 0),
(5, 'House', 20170420181703, 0),
(6, 'Trap', 1505416085214, 1505416085214),
(7, 'EDM', 1505416152783, 1505416152783),
(8, 'Shoegaze', 1505416173188, 1505416173188),
(9, 'Instrumental', 1505416197184, 1505416197184);

-- --------------------------------------------------------

--
-- Table structure for table `issue_tracker`
--

CREATE TABLE `issue_tracker` (
  `id` int(11) UNSIGNED NOT NULL,
  `creation_timestamp` bigint(15) UNSIGNED NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `alias` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `issue_type` int(11) UNSIGNED NOT NULL,
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `issue_types`
--

CREATE TABLE `issue_types` (
  `id` int(10) UNSIGNED NOT NULL,
  `type` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `message_list`
--

CREATE TABLE `message_list` (
  `id` int(11) UNSIGNED NOT NULL,
  `message` varchar(2000) COLLATE utf8_unicode_ci NOT NULL,
  `timestamp` bigint(15) UNSIGNED NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  `sender_id` int(11) UNSIGNED NOT NULL,
  `chat_id` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `message_list`
--

INSERT INTO `message_list` (`id`, `message`, `timestamp`, `is_read`, `sender_id`, `chat_id`) VALUES
(163, 'yo.yo yo my nigga\n\n', 1504598918086, 0, 303346740, 52),
(187, 'Sup', 1504613456097, 0, 303346740, 52),
(224, 'hey sorry for the late reply xD', 1506797376126, 0, 303346736, 58),
(226, 'greetings', 1507241533211, 0, 303346737, 56),
(227, 'sup bru!!!!', 1507241546898, 0, 303346737, 61);

-- --------------------------------------------------------

--
-- Table structure for table `pages`
--

CREATE TABLE `pages` (
  `id` int(10) UNSIGNED NOT NULL,
  `template` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `slug` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `extras` text COLLATE utf8_unicode_ci,
  `created_timestamp` bigint(15) UNSIGNED DEFAULT NULL,
  `updated_timestamp` bigint(15) UNSIGNED DEFAULT NULL,
  `deleted_timestamp` bigint(15) UNSIGNED DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ratings`
--

CREATE TABLE `ratings` (
  `id` int(11) NOT NULL,
  `title` varchar(500) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `removed_status`
--

CREATE TABLE `removed_status` (
  `post_id` int(11) UNSIGNED NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  `timestamp` bigint(15) UNSIGNED NOT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `extra_info` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `liked` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `removed_status`
--

INSERT INTO `removed_status` (`post_id`, `user_id`, `timestamp`, `status`, `extra_info`, `liked`) VALUES
(37, 303346740, 1503749803064, 'Yo gents!!', 'Not implemented', 0),
(35, 303346740, 1503324862353, 'What\'s the scope for informatics?? ', 'Not implemented', 0),
(35, 303346740, 1503324862353, 'What\'s the scope for informatics?? ', 'Not implemented', 0),
(34, 303346740, 1502175116689, 'Hello World\n', NULL, 1),
(33, 303346737, 1502014019260, 'That was a really good studio session I had with John. I hope to work with him again soon.', NULL, 10),
(38, 303346737, 1504467656592, 'That remix from John was really good.', 'Not implemented', 0),
(42, 303346740, 1504606241290, 'Love music, Love Life!', 'Not implemented', 0),
(44, 303346740, 1504614306608, 'Making life happen on EV', 'Not implemented', 0),
(24, 303346727, 1501436641541, 'Music is life :P', NULL, 0),
(36, 303346740, 1503324910731, 'What\'s the scope for the Infos semester test?', 'Not implemented', 0),
(43, 303346740, 1504610479029, 'Making history!!', 'Not implemented', 0),
(25, 303346727, 1501437322740, 'Check me out bro', NULL, 0),
(41, 303346740, 1504599137530, 'Awe where the next music fest at?\n', 'Not implemented', 0);

-- --------------------------------------------------------

--
-- Table structure for table `reviews`
--

CREATE TABLE `reviews` (
  `id` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` varchar(500) NOT NULL,
  `rating_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `skills`
--

CREATE TABLE `skills` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skills`
--

INSERT INTO `skills` (`id`, `name`) VALUES
(7, 'Backup vocalist'),
(0, 'DJ'),
(9, 'Drummer'),
(13, 'Event Management'),
(12, 'Flute'),
(8, 'Guitarist'),
(2, 'Mixer'),
(23, 'Music Critic'),
(11, 'Piano'),
(3, 'Producer'),
(5, 'Rapper'),
(10, 'Saxophone'),
(4, 'Singer'),
(1, 'Song writer'),
(19, 'Sound Engineer'),
(6, 'Vocalist');

-- --------------------------------------------------------

--
-- Table structure for table `status_likes`
--

CREATE TABLE `status_likes` (
  `id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `likerUser_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status_likes`
--

INSERT INTO `status_likes` (`id`, `status_id`, `likerUser_id`) VALUES
(7, 28, 303346733),
(200, 30, 303346736),
(47, 31, 303346736),
(171, 31, 303346737),
(169, 31, 303346765),
(33, 33, 303346733),
(71, 33, 303346737),
(5, 34, 303346727),
(6, 35, 303346727),
(30, 36, 303346733),
(1, 37, 303346727),
(11, 37, 303346733),
(67, 37, 303346740),
(142, 38, 303346727),
(105, 38, 303346733),
(91, 38, 303346737),
(144, 38, 303346740),
(203, 39, 303346733),
(188, 39, 303346736),
(148, 39, 303346740),
(145, 40, 303346727),
(202, 40, 303346733),
(163, 42, 303346740),
(149, 43, 303346737),
(159, 43, 303346740),
(206, 47, 303346733),
(212, 47, 303346737),
(215, 55, 303346736);

-- --------------------------------------------------------

--
-- Table structure for table `status_list`
--

CREATE TABLE `status_list` (
  `id` int(11) UNSIGNED NOT NULL,
  `user_id` int(11) UNSIGNED NOT NULL,
  `timestamp` bigint(15) UNSIGNED NOT NULL,
  `status` varchar(2000) COLLATE utf8_unicode_ci NOT NULL,
  `extra_info` varchar(2000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `liked` int(11) UNSIGNED NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `status_list`
--

INSERT INTO `status_list` (`id`, `user_id`, `timestamp`, `status`, `extra_info`, `liked`) VALUES
(22, 303346726, 1501148073035, 'Hello World', NULL, 5),
(23, 123, 1501168440873, 'hnvhbg\n\n\n\n', NULL, 0),
(26, 303346716, 1501437349188, 'looking good Bro!!', NULL, 4),
(27, 303346716, 1501437359574, 'This is so cool', NULL, 0),
(28, 303346716, 1501698067906, 'Good vibes', NULL, 9),
(29, 303346727, 1501841906343, 'I am in E ring \n', NULL, 19),
(30, 303346736, 1501938941227, 'Making a post', NULL, 1),
(31, 303346733, 1501939047834, 'Good work that track!', NULL, 13),
(32, 303346727, 1501957048563, 'I was in my 8th grade math class. All year a particular student, known for being a little wacky, had been occasionally pretending to be Pikachu. Some days he would sit there working on stuff and someone would ask him a question or whater and he\'d just respond \'Pika? Piiiika CHUU\' or any other combination. Well, one day late in the year we are working on our assignment after recieving the lecture and said kid starts making some weird noises. Starts to shake a little bit. At first I thought maybe he was starting to seize, but just as it starts to get frighteningly violent he stops still as a rock and practically shouts "RAICHU!!!\' After a couple seconds of stunned silence, our teacher simply shakes his head and says, \'Derrick, principles office, now.', NULL, 2),
(39, 303346740, 1504557569318, 'Status 1 test', 'Not implemented', 2),
(40, 303346727, 1504557592603, 'status 2 test', 'Not implemented', 1),
(45, 303346783, 1505295167754, '\nVenny is here.', 'Not implemented', 0),
(46, 303346733, 1505912293045, 'Status test #3', 'Not implemented', 0),
(47, 303346733, 1505913947338, 'Test Status #3', 'Not implemented', 2),
(48, 303346727, 1506200624025, '3 weeks left guys', 'Not implemented', 0),
(49, 303346727, 1506258015025, 'Good Music = Good Day', 'Not implemented', 0),
(50, 303346727, 1506287008338, 'Going to kill some ears soon #Woop', 'Not implemented', 0),
(51, 303346727, 1506287260856, 'Going mental tonight', 'not implemented', 0),
(52, 303346727, 1506287424686, 'Yo RzCodec!!!!', 'Not implemented', 0),
(53, 303346740, 1506840098358, 'Okay Soo load shedding is a vibe killer ...', 'Not implemented', 0),
(54, 303346736, 1506890427172, 'Test post #4', 'Not implemented', 0),
(55, 303346736, 1506940015754, 'testing pb', 'Not implemented', 1),
(56, 303346737, 1507241521269, 'more testing', 'Not implemented', 0);

-- --------------------------------------------------------

--
-- Table structure for table `status_reports`
--

CREATE TABLE `status_reports` (
  `id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `reporterUser_id` int(11) NOT NULL,
  `reason` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `status_reports`
--

INSERT INTO `status_reports` (`id`, `status_id`, `reporterUser_id`, `reason`) VALUES
(1, 37, 303346740, 'Unknown'),
(2, 37, 303346727, 'Unknown'),
(6, 35, 303346727, 'Unknown'),
(9, 34, 303346727, 'Unknown'),
(10, 34, 303346733, 'Unknown'),
(11, 33, 303346733, 'Unknown'),
(14, 38, 303346737, 'Unknown'),
(21, 38, 303346733, 'Unknown'),
(33, 43, 303346740, 'Unknown'),
(34, 42, 303346740, 'Unknown'),
(35, 41, 303346740, 'Unknown'),
(39, 44, 303346783, 'Unknown'),
(42, 36, 303346740, 'Unknown'),
(43, 25, 303346740, 'Unknown'),
(44, 24, 303346740, 'Unknown'),
(52, 30, 303346737, 'Unknown'),
(53, 39, 303346737, 'Unknown'),
(54, 31, 303346736, 'Unknown'),
(58, 55, 303346736, 'Unknown');

-- --------------------------------------------------------

--
-- Table structure for table `studios`
--

CREATE TABLE `studios` (
  `id` int(11) UNSIGNED NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `description` varchar(2000) COLLATE utf8_unicode_ci NOT NULL,
  `cost_per_hour` decimal(10,0) UNSIGNED NOT NULL,
  `name` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `owner` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `website` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` varchar(14) COLLATE utf8_unicode_ci DEFAULT NULL,
  `added_timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `studios`
--

INSERT INTO `studios` (`id`, `latitude`, `longitude`, `description`, `cost_per_hour`, `name`, `owner`, `website`, `phone_number`, `added_timestamp`) VALUES
(1, -26.1048, 27.9769, 'Five6seven8 is based in Randburg, Johannesburg offering various styles such as Hip Hop, Contemporary, Burlesque, Modern, Ballet, Nia.', '50', 'Five6seven8 Dance Studio', 'Mr.Smith', 'https://www.five6seven8.co.za/', '213123123', 0),
(2, -26.1048, 27.9769, 'Five6seven8 is based in Randburg, Johannesburg offering various styles such as Hip Hop, Contemporary, Burlesque, Modern, Ballet, Nia.', '50', 'Five6seven8 Dance Studio', 'Mr.Smith', 'https://www.five6seven8.co.za/', '0761162082', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `firstname` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `genre_id` int(11) UNSIGNED NOT NULL,
  `song_link` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `pardons` int(11) UNSIGNED DEFAULT '0',
  `distance_id` int(10) UNSIGNED NOT NULL,
  `join_timestamp` bigint(15) UNSIGNED NOT NULL,
  `is_banned` tinyint(1) UNSIGNED DEFAULT '0',
  `last_login_timestamp` bigint(15) UNSIGNED NOT NULL,
  `profilepic_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` char(60) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `admin` tinyint(1) NOT NULL DEFAULT '0',
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `firstname`, `surname`, `email`, `username`, `genre_id`, `song_link`, `latitude`, `longitude`, `pardons`, `distance_id`, `join_timestamp`, `is_banned`, `last_login_timestamp`, `profilepic_url`, `password`, `description`, `admin`, `city`) VALUES
(303346716, 'Kog', 'Maw', 'kog@gmail.com', 'kogmaw', 2, NULL, 27.86431065939382, -26.343118096512818, 0, 3, 1500930875194, 0, 1507282606054, NULL, '$2a$10$4T5KUnFKIxLIeiiMM8a5Ked9HJC.x8IuqR7yhU6.9fdMf44HMHlfW', 'This is the time to be aliveeeeeeeeeeee', 1, NULL),
(303346727, 'd', 'k', 'd@v.com', 'dv', 2, NULL, 27.994365000000002, -26.183511666666664, 0, 6, 1501428707216, 0, 1507286275846, NULL, '$2a$10$WuJBhKgB6KeHuQHLdPMBX.6R.89kv5jjSysN9dJZyeeSPO2WHXDfS', 'I sing my heart for the people to hear', 1, 'Johannesburg, South Africa'),
(303346733, 'Ronald', 'Lai', '201433999@student.uj.ac.za', 'rzCodec', 5, NULL, 28.104379953832243, -26.114949675209033, 0, 2, 1501785333120, 0, 1507278983614, NULL, '$2a$10$YbzZb35Pb65ciDaUzC4xzub0D2jkGIF2CzEOa7XgzlHfmZiJNhbUa', 'Hello', 0, 'Johannesburg, South Africa'),
(303346734, 'Admin', 'Example', 'admin@gmail.com', 'AdminExample', 0, NULL, NULL, NULL, 0, 1, 1501838196394, 0, 1504557541231, NULL, '$2a$10$2jwq5eGbb.7lvnQi8TUaKOp8js0gcItrQaosLhcibzbyLMlyim.5.', 'This is admin example', 1, NULL),
(303346735, 'Marc', 'Zuze', 'marczuze@gmail.com', 'adminMarc', 2, NULL, NULL, NULL, 0, 25, 1501838865442, 0, 1504610089630, NULL, '$2a$10$Ng3NSrls2edrj.uos96o8.9PA0HPnuwXu7E1T54kl317wK.sn4OdG', 'nothing for now', 0, NULL),
(303346736, 'John', 'Crester', 'john12@gmail.com', 'theJC', 0, NULL, 28.108129109566878, -26.116468596685962, 0, 4, 1501863448284, 0, 1507279026858, NULL, '$2a$10$hG8Eq8x78w1HdP9iP6.a1OnHeRtpzj5OV5iYkzicIRTfSJqDcfVC2', 'The classic songs from the 90\'s can to be remixed and refined with pizza space.', 0, 'Johannesburg, South Africa'),
(303346737, 'Bob', 'Breck', 'bob2@gmail.com', 'mrBob', 2, NULL, 28.107955693761546, -26.11649435791363, 0, 6, 1502013584135, 0, 1507277814349, NULL, '$2a$10$ZiwkkMghXzLBlGRiGVoqze/aQ9YGzKp8JGCeK1675KEupi.6IPyyu', 'This is a description describing that a description can be very long depending on what description the user wants to show to other users. Maybe I can extend this description even more #Testing text bruh', 0, 'Johannesburg, South Africa'),
(303346738, 'Test12F', 'Test12S', 'Test12@gmail.com', 'Test12U', 0, NULL, NULL, NULL, 0, 1, 1502131527955, 1, 1502131527955, NULL, '$2a$10$95piiqtRnvVPToZGxelx7eC9i.JDHvOELxFI9poM7EcXjPjrBTA8.', 'dfsdfsdfsdfsdfsdfsdfsfsfsfsfsdf', 0, NULL),
(303346739, 'test13f', 'test13s', 'test13@gmail.com', 'test13u', 0, NULL, NULL, NULL, 0, 3, 1502132601338, 1, 1502132601338, NULL, '$2a$10$VX0YJzEJKFFs/0c5SqO1vef6I58PIyXXgDAsDOx/TK2KRJ/Omjw.e', 'sdfsdfsdfsdfsdfsdfsdfsdfsdfsd', 0, NULL),
(303346740, 'Devandrin', 'Kuni', '201320596@student.uj.ac.za', 'Vanneh', 3, NULL, 27.86436300401383, -26.343192585973522, 0, 4, 1502132956508, 0, 1507288467450, NULL, '$2a$10$8P5TettFRm8RbXOrpqKtXu13qqSXPREflmyOjE2evk6I1RhkgSMie', 'Just wanna jazz to beats', 0, 'Lenasia, South Africa'),
(303346741, 'John', 'Doe', 'JDough@gmail.com', 'JDough', 2, NULL, NULL, NULL, 0, 3, 1502143139800, 0, 1502143139800, NULL, '$2a$10$B80Dixgv2TEOBUWyab8Dl.BTUn17YUoTpxUKojBq/EkJxxpsngHzG', 'JDough for the money', 0, NULL),
(303346743, 'ctest', 'ci', 'c@gmail.com', 'charlie', 5, NULL, -26.1210302, 28.1050376, 0, 5, 1503167725704, 1, 1504177509524, NULL, '$2a$10$9WR41eeJeWv3jG0m1ZVUPO.O4r2/yf.nUiYZhrz7lf4OJRNGEC8Xu', 'I really enjoy travelling and meeting new musicians along the way!', 0, NULL),
(303346757, 'Hello', 'World', 'Hello@world.com', 'Hello World', 0, NULL, NULL, NULL, 0, 1, 1503739930040, 0, 1503776685269, NULL, '$2a$10$2X8XLfU/19kI1o52ym.2UOlg4AwWUrRd53wXCaQsKkcNZf5od6IjC', 'nothing for now', 0, NULL),
(303346758, 'donny', 'den', 'don@gmail.com', 'theDon', 4, NULL, -26.11650164330849, 28.108026688755523, 0, 4, 1503776838901, 0, 1503776896731, NULL, '$2a$10$jd5RFhhITVsCKVgi6C37a.C3FI4iIk0ZZQwT8SrTANOeG8F.K43Qu', 'Making the best music is always a challenge but I like it!\n', 0, NULL),
(303346759, 'Gordon', 'Smith', 'Gordon@gmail.com', 'Gordon Smith', 0, NULL, NULL, NULL, 0, 6, 1503776975859, 0, 1503796610098, NULL, '$2a$10$D32GpM8S4tqp91vLlYNvsu2W7gmViDJDx5Av4zTehIZI3dPHpFxaq', 'nothing for now', 0, NULL),
(303346760, 'Test', 'Tester', 'testing@123.com', 'Test Tester', 0, NULL, NULL, NULL, 0, 1, 1503830357395, 0, 1503831989922, NULL, '$2a$10$rCveMBjoQ8lcwluyoB8Vu.GynlRBPXAx/4e1PvaGFjbEj3HVKACh2', 'Testing 123... Is this Mic on?', 0, NULL),
(303346762, 'Chalie', 'Anne', 'Charlie@anonymo.us', 'Anonymous Charles', 0, NULL, NULL, NULL, 0, 1, 1503858148624, 0, 1503858151629, NULL, '$2a$10$hcJlvz.Qy3n061Y01XFzzetvCmGyoQxJNPcLAOXWssVG281s454rC', 'nothing for now', 0, NULL),
(303346764, 'Wonder', 'Lowe', 'LoweWonders@gmail.com', 'Lowe Wonders', 0, NULL, NULL, NULL, 0, 1, 1503900535862, 0, 1503900535862, NULL, '$2a$10$Tfy95kE0eA6460Kx73XIgu84dvF5EnXgkdhul0xZmBtYKZ23UNc9K', 'I wonder', 0, NULL),
(303346765, 'ztest', 'zinger', 'z@gmail.com', 'zing', 1, NULL, 28.108082122965616, -26.116410483437917, 0, 5, 1504122740470, 0, 1507115402632, NULL, '$2a$10$1CS5mj2umeVr2KqzA0o7aOz/Nq7ytVAqu12/y4fdKEjvLTN9PweMe', 'The beat of a track must flow like water.', 0, 'Johannesburg, South Africa'),
(303346778, 'zane', 'zo', 'zi@gmail.com', 'zanez', 2, NULL, -26.11649772426047, 28.108070033180866, 0, 5, 1504125187103, 0, 1504126708397, NULL, '$2a$10$vGakNYPyZ.HiAbxcCJ3gRuw33m7DGYlxIuhZAETd.sAZDZN2YEXFG', 'I like to test music.\n', 0, NULL),
(303346779, 'yackon', 'hek', 'y@gmail.com', 'yice', 2, NULL, 28.108084069253504, -26.11645085260664, 0, 2, 1504468039724, 0, 1504468098180, NULL, '$2a$10$RLbO0VESTXsA3SmJlI7MVusnC36BxP9Iu.52LqvhpEo.SwtRQlWum', 'I want to make awesome remixes for the classic tracks.', 0, NULL),
(303346780, 'Dev', 'K', 'devandrink@yahoo.com', 'D_v_D', 3, NULL, 28.059293333333336, -26.314746666666665, 0, 4, 1504557239966, 0, 1504561477281, NULL, '$2a$10$bGkSUmjLuo2OWEzOOzjEVO97nX7E.spE90vty9WcZwdVhDpPych1a', 'Just wanna make music, live life in the shoes of a master musician', 0, 'Johannesburg South, South Africa'),
(303346781, 'Kiveshan', 'Thumbiran', 'jives.kt@gmail.com', 'Dredd25', 3, NULL, 27.941031157086908, -26.31879320053992, 0, 5, 1504593007616, 0, 1504594595202, NULL, '$2a$10$WwJIJqVdmdOmwD1FNSxMFeVYa0JP9hwmQpa62Q68p7GNtWFjM69si', 'suicidal but I make dank memes\nalso I like chicken\n', 0, 'Greater Johannesburg Metropolitan Area, South Africa'),
(303346782, 'jerry', 'albun', 'j@gmail.com', 'Jerries', 1, NULL, 28.108093756356016, -26.116373181206114, 0, 3, 1504604425160, 0, 1505054734740, NULL, '$2a$10$e8ljktKnYy0NBhyj5fR50Op9g0xtSYEMtuDYNYsbYHeERb/5zlv8a', 'I gotta make music', 0, 'Johannesburg, South Africa'),
(303346783, 'venny', 'vi', 'v@gmail.com', 'vEnny', 5, NULL, 27.996056756351045, -26.1855066192825, 0, 5, 1504607485950, 0, 1505297593675, NULL, '$2a$10$ofLaOQOLa6b.05RUo4zvFuPmnIoFBRAvi4SPKPPIbQT5yPpbDtAHe', 'I like to make remixes of deep house songs.\n\n', 0, 'Johannesburg, South Africa');

-- --------------------------------------------------------

--
-- Table structure for table `user_locations`
--

CREATE TABLE `user_locations` (
  `id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `country_abbreviation` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `postal_code` int(11) NOT NULL,
  `place` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `created_timestamp` bigint(15) UNSIGNED DEFAULT NULL,
  `updated_timestamp` bigint(15) UNSIGNED DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_locations`
--

INSERT INTO `user_locations` (`id`, `user_id`, `country`, `country_abbreviation`, `postal_code`, `place`, `longitude`, `latitude`, `created_timestamp`, `updated_timestamp`) VALUES
(1, 1, 'South Africa', 'ZA', 8000, 'Cape Town', 18.4139, -33.9161, 20170222203732, 20170403201444),
(2, 3, 'United States', 'US', 10312, 'Staten Island', -74.1792, 40.5457, 20170223082119, 20170223082119),
(3, 2, 'Finland', 'FI', 33560, 'Tampere', 23.75, 61.5, 20170223174439, 20170223174439),
(4, 7, 'United States', 'US', 32566, 'Navarre', -86.8926, 30.4212, 20170308230830, 20170308230830),
(5, 8, 'United States', 'US', 23513, 'Norfolk', -76.2396, 36.8914, 20170309234230, 20170309234230),
(6, 9, 'Germany', 'DE', 13189, 'Berlin', 13.423, 52.5677, 20170324174107, 20170324174107),
(7, 10, 'South Africa', 'ZA', 184, 'Pretoria', 28.2294, -25.7069, 20170402005544, 20170403141701),
(8, 12, 'South Africa', 'ZA', 2194, 'Randburg', 27.9833, -26.1, 20170404171821, 20170404171821);

-- --------------------------------------------------------

--
-- Table structure for table `user_reports`
--

CREATE TABLE `user_reports` (
  `id` int(11) NOT NULL,
  `userThatReported` int(11) NOT NULL,
  `userThatGotReported` int(11) NOT NULL,
  `reason` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_reports`
--

INSERT INTO `user_reports` (`id`, `userThatReported`, `userThatGotReported`, `reason`) VALUES
(2, 303346740, 303346738, 'This person is posting vulgar ');

-- --------------------------------------------------------

--
-- Table structure for table `user_settings`
--

CREATE TABLE `user_settings` (
  `user_id` int(10) UNSIGNED NOT NULL,
  `tracks` tinyint(1) NOT NULL DEFAULT '1',
  `friends` tinyint(1) NOT NULL DEFAULT '1',
  `promoted_artists` tinyint(1) NOT NULL DEFAULT '1',
  `interactions` tinyint(1) NOT NULL DEFAULT '1',
  `updated_timestamp` bigint(15) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_skills`
--

CREATE TABLE `user_skills` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_skills`
--

INSERT INTO `user_skills` (`id`, `user_id`, `skill_id`) VALUES
(289, 303346716, 0),
(290, 303346716, 8),
(283, 303346727, 1),
(287, 303346727, 4),
(288, 303346727, 13),
(279, 303346733, 7),
(281, 303346733, 9),
(282, 303346733, 12),
(278, 303346733, 19),
(280, 303346733, 23),
(252, 303346736, 0),
(253, 303346736, 2),
(251, 303346736, 5),
(277, 303346736, 7),
(238, 303346736, 8),
(239, 303346736, 11),
(274, 303346736, 19),
(249, 303346737, 0),
(235, 303346737, 1),
(228, 303346737, 2),
(231, 303346737, 3),
(234, 303346737, 5),
(256, 303346737, 8),
(248, 303346737, 9),
(284, 303346737, 10),
(272, 303346737, 19),
(276, 303346737, 23),
(260, 303346740, 2),
(261, 303346740, 3),
(268, 303346740, 9),
(265, 303346740, 10),
(262, 303346740, 13),
(243, 303346743, 8),
(244, 303346743, 11),
(212, 303346759, 3),
(226, 303346762, 1),
(225, 303346762, 3),
(224, 303346762, 4),
(254, 303346765, 3),
(255, 303346765, 4),
(250, 303346765, 11),
(271, 303346765, 15),
(241, 303346778, 3),
(240, 303346778, 7),
(257, 303346779, 0),
(259, 303346779, 3),
(263, 303346780, 3),
(264, 303346781, 6),
(266, 303346782, 3),
(267, 303346783, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Address`
--
ALTER TABLE `Address`
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indexes for table `ApplicationLog`
--
ALTER TABLE `ApplicationLog`
  ADD PRIMARY KEY (`_ID`);

--
-- Indexes for table `broadcast_list`
--
ALTER TABLE `broadcast_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `chats`
--
ALTER TABLE `chats`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `chatID` (`id`);

--
-- Indexes for table `distances`
--
ALTER TABLE `distances`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_attendees`
--
ALTER TABLE `events_attendees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_invites`
--
ALTER TABLE `events_invites`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_locations`
--
ALTER TABLE `events_locations`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `events_id` (`events_id`);

--
-- Indexes for table `events_requests`
--
ALTER TABLE `events_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_responses`
--
ALTER TABLE `events_responses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_reviews`
--
ALTER TABLE `events_reviews`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_skills`
--
ALTER TABLE `events_skills`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_types`
--
ALTER TABLE `events_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `events_visibilitys`
--
ALTER TABLE `events_visibilitys`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `followers`
--
ALTER TABLE `followers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `followers` (`user_id`,`liked_id`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD UNIQUE KEY `Name` (`name`);

--
-- Indexes for table `issue_tracker`
--
ALTER TABLE `issue_tracker`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `issueID` (`id`);

--
-- Indexes for table `issue_types`
--
ALTER TABLE `issue_types`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `message_list`
--
ALTER TABLE `message_list`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `messageID` (`id`);

--
-- Indexes for table `pages`
--
ALTER TABLE `pages`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `skills`
--
ALTER TABLE `skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `status_likes`
--
ALTER TABLE `status_likes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `statusLikes` (`status_id`,`likerUser_id`);

--
-- Indexes for table `status_list`
--
ALTER TABLE `status_list`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `statusID` (`id`);

--
-- Indexes for table `status_reports`
--
ALTER TABLE `status_reports`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `statusReports` (`status_id`,`reporterUser_id`);

--
-- Indexes for table `studios`
--
ALTER TABLE `studios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `studioID` (`id`),
  ADD UNIQUE KEY `phoneNumber` (`phone_number`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ID` (`id`),
  ADD UNIQUE KEY `Email` (`email`),
  ADD UNIQUE KEY `email_2` (`email`);

--
-- Indexes for table `user_locations`
--
ALTER TABLE `user_locations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_locations_user_id_foreign` (`user_id`);

--
-- Indexes for table `user_reports`
--
ALTER TABLE `user_reports`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userReported` (`userThatReported`,`userThatGotReported`);

--
-- Indexes for table `user_settings`
--
ALTER TABLE `user_settings`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_skills`
--
ALTER TABLE `user_skills`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userSkills` (`user_id`,`skill_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ApplicationLog`
--
ALTER TABLE `ApplicationLog`
  MODIFY `_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `broadcast_list`
--
ALTER TABLE `broadcast_list`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `chats`
--
ALTER TABLE `chats`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;
--
-- AUTO_INCREMENT for table `distances`
--
ALTER TABLE `distances`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `events_attendees`
--
ALTER TABLE `events_attendees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `events_invites`
--
ALTER TABLE `events_invites`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
--
-- AUTO_INCREMENT for table `events_locations`
--
ALTER TABLE `events_locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `events_requests`
--
ALTER TABLE `events_requests`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `events_responses`
--
ALTER TABLE `events_responses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `events_reviews`
--
ALTER TABLE `events_reviews`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
--
-- AUTO_INCREMENT for table `events_skills`
--
ALTER TABLE `events_skills`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;
--
-- AUTO_INCREMENT for table `events_types`
--
ALTER TABLE `events_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `followers`
--
ALTER TABLE `followers`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=181;
--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `issue_tracker`
--
ALTER TABLE `issue_tracker`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `issue_types`
--
ALTER TABLE `issue_types`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `message_list`
--
ALTER TABLE `message_list`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=228;
--
-- AUTO_INCREMENT for table `pages`
--
ALTER TABLE `pages`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `ratings`
--
ALTER TABLE `ratings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `reviews`
--
ALTER TABLE `reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `skills`
--
ALTER TABLE `skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `status_likes`
--
ALTER TABLE `status_likes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=220;
--
-- AUTO_INCREMENT for table `status_list`
--
ALTER TABLE `status_list`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;
--
-- AUTO_INCREMENT for table `status_reports`
--
ALTER TABLE `status_reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
--
-- AUTO_INCREMENT for table `studios`
--
ALTER TABLE `studios`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=303346784;
--
-- AUTO_INCREMENT for table `user_locations`
--
ALTER TABLE `user_locations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `user_reports`
--
ALTER TABLE `user_reports`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `user_settings`
--
ALTER TABLE `user_settings`
  MODIFY `user_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_skills`
--
ALTER TABLE `user_skills`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=291;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
