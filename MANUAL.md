# ATSAssist解説

## IFTTT

### That

#### JavaScript

設定されたスクリプトをサーバー側から呼び出します。一度失敗すると修正されるまで再度実行されません。 \
例

```js
importPackage(Packages.jp.kaiz.atsassistmod.api);

function doThat(tile, train, first) {
    ControlTrain.logger("test");
}
```
