中文 | [English][2]

# Evil Hide
隐藏app的app。用处自己挖掘。

![1](./demo.gif)

### 特点：
- 可以隐藏软件自身的图标，并通过在拨号盘输入暗号启动应用
- 不需要root！（仅限`原生Android 5.0`以上，一般而言，Nexus家族是可以使用的，包括但不限于Nexus 4、Nexus 5、Nexus 5x、Nexus 6P等，第三方厂商自己修改过的ROM不可使用，包括但不限于三星，小米，华为等）

### 原理
在Android 5.0以下使用以下命令显示和隐藏（需要root权限）：
```
adb shell pm endable <package_name>
adb shell pm disable <package_name>
```
在Android 5.0及以上使用以下命令显示和隐藏（不需要root）:
```
adb shell pm hide <package_name>
adb shell pm unhide <packag_name>
```

区别：
`hide`命令相当于`uninstall -k`卸载应用但是保留数据

### 用法：
本软件的默认启动密码是`#1234`
启动方式：
打开拨号盘，输入`#1234`，再拨出，就可以启动本应用了，然后在应用列表里选择应用是否隐藏即可

###待做列表：
- [x] Android 6.0的权限管理
- [x] 当检测到手机是Android 4.4以上时，使用`pm hide`代替`pm disable`
- [ ] 使用密码启动被隐藏的应用

[1]: https://github.com/blackbbc/Evil-Hide/blob/master/README.md
[2]: https://github.com/blackbbc/Evil-Hide/blob/master/README_en.md
