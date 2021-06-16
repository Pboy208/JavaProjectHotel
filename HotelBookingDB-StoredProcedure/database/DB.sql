-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 16, 2021 lúc 12:23 PM
-- Phiên bản máy phục vụ: 10.4.18-MariaDB
-- Phiên bản PHP: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `latestdb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `phone` varchar(40) NOT NULL,
  `name` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `phone`, `name`, `email`, `password`, `username`) VALUES
(1, '0969929273', 'Tuan Dung', 'DungManager@gmail.com', '123', 'dungmanager'),
(16, '0987729917', 'Phuong Nguyen ahah', 'PhuongNguyenManager@gmail.com', '123', 'phuongmanager'),
(17, '1234567890', 'dung', 'tuandungnguyen2901@gmail.com', '123', 'dung123'),
(18, '12345679', 'dung', 'asd', '123', 'dungmanage'),
(19, '12345678', 'dung', 'asd', '123', 's'),
(22, '1234567801', 'dung', 'asd', '123', 's2'),
(26, '1234567899', 'dung', 'asd', '123', 'dungmanager1001'),
(27, '1234567877', 'dung', 'asd', '123', 'dungmanager12'),
(30, '12334', 'dung', 'asd', '123', 'phuongmanager1'),
(37, '12345678912345', 'dung', 'asd', '123', 'dungmanager1235'),
(38, '0987729999', 'testing', 'testing@gmail.com', '123', 'testing'),
(39, '0987729998', 'testing', 'testing@gmail.com', '123', 'testingmanager'),
(44, '0987729915', 'testing', 'testing@gmail.com', '123', 'testingmanagerver2'),
(47, '0987728888', 'testingComplete', 'testingComplete@gmail.com', 'abc', 'testingmanagerver3');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
