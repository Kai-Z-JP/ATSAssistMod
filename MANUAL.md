# ATSAssist解説

## IFTTT

### That

#### JavaScript

設定されたスクリプトをサーバー側から呼び出します。一度失敗すると修正されるまで再度実行されません。 \
例

```js
importPackage(Packages.jp.kaiz.atsassistmod.api);

/**
 * @param {TileEntityIFTTT} tile
 * @param {EntityTrainBase} train nullが入る場合があります
 * @param {boolean} first 初回の場合trueになります
 */
function doThat(tile, train, first) {
    ControlTrain.logger("test");
}
```
