#Evil Hide
> 这个应用可以隐藏其他的应用图标哟

隐藏app的app。用处自己挖掘。

###特点：
- 本软件本身没有图标，需通过输入暗号进入。
- <del>不用root（仅限Android 4.4以上）</del>（待做）

###原理
在Android 4.4以下使用以下命令显示和隐藏（需要root权限）：
```
adb shell pm endable {package_name}
adb shell pm disable {package_name}
```
在Android 4.4以上使用以下命令显示和隐藏（不需要root）:
```
adb shell pm hide {package_name}
adb shell pm unhide {packag_name}
```
区别：
`hide`命令相当于`uninstall -k`卸载应用但是保留数据
`disable`...

###用法：
本软件的默认启动密码是`#1234`
启动方式：
打开拨号盘，输入`#1234`，再拨出，就可以启动本应用了，然后在应用列表里选择应用是否隐藏即可

###待做列表：
- [x] Android 6.0的权限管理
- [ ] 当检测到手机是Android 4.4以上时，使用`pm hide`代替`pm disable`
- [ ] 使用密码启动应用
- [ ] 应用关闭后自动隐藏
