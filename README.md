# SudokuGame<br>
## 这是一个Android移动游戏开发的数独项目，实现了针对各个用户的具有不同难度选择的数独游戏开发，添加了成绩排名以及时间控制等附加功能。<br>
## 项目实现功能如下：<br>
1.棋盘9*9，根据选取游戏的难度随机生成题目<br>
2.支持登录注册和成绩记录<br>
3.支持提示功能，提示空格处不可填的数字，提示游戏成功和所用时间<br>
4.支持每手定时和每局定时功能，并且根据用时长短实现成绩排行榜<br>
5.支持连续多盘功能，并且可以继续进行未完成的游戏<br>
6.支持人机对弈功能：实现了能够根据当前九宫格内的数字判断空格位置可用的数字，该功能也可关闭。<br>
7.支持背景音乐且该功能可关闭<br>
8.不支持联网功能<br>
## 项目框架介绍：<br>
AboutShuDuGame.java--介绍数独游戏规则对话框功能的实现<br>
ShuDuGame.java—游戏流程控制<br>
MyView.java—游戏界面对话框等的显示<br>
Game.java—游戏逻辑计算功能<br>
GetTime.java—时间控制功能<br>
Login.java—登录功能<br>
RegisterUser.java—注册功能<br>
Music.java—音乐播放功能<br>
Rank.java/RankSql.java—成绩排名功能<br>
ShuDuGameSetting.java—游戏设置功能<br>
## 更多相关信息请看工程文件中的项目说明.PPTX<br>
