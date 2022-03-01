/**
 * 通用js方法封装处理
 * Copyright (c) 2022-03
 */

const desktop = {
    canvas: null,
    connection: null,
    // 状态：standby,connected,controlling,exchanging,disconnected
    state: 'standby',
    init: function (id, userId) {
        console.log(id, userId);
        const self = this;
        if (this.connection != null) return;
        this.connect(userId);
        this.canvas = document.getElementById(id).getContext('2d');
        this.canvas.width = 1360;
        this.canvas.height = 768;
    },

    connect: function (userId) {
        console.log(userId);
        const self = this;
        this.connection = new WebSocket('ws://' + location.host + '/desktop/wss/' + userId);
        this.connection.onopen = function () {
            self._onopen.apply(self, arguments);
        };
        this.connection.onmessage = function () {
            self._onmessage.apply(self, arguments);
        };
        this.connection.onclose = function () {
            self._onclose.apply(self, arguments);
        };
        this.connection.onerror = function () {
            self._onerror.apply(self, arguments);
        };
    },
    _onopen: function () {
        this._connected();
    },
    _onmessage: function (resp) {
        const self = this;
        if (resp.data instanceof Blob) {
            // const packet = new Uint8Array(resp.data);
            // this.frames.push(packet);
            this.readBlobAsDataURL(resp.data, function (dataUrl){
                console.log(dataUrl);
                const img = new Image();
                img.onload = function () {
                    self.canvas.drawImage(img, 0, 0, self.canvas.width, self.canvas.height);
                }
                img.src = dataUrl
                // this.canvas.drawImage(packet, 0, 0, this.canvas.width, this.canvas.height);
            });

        }
        console.log(resp);
    },
    _onclose: function () {
        this._disconnected();
    },
    _onerror: function () {
    },

    // 状态变更
    _exchanging: function () {
        this.state = 'exchanging';
    },
    _controlling: function () {
        this.state = 'controlling';
    },
    _connected: function () {
        this.state = 'connected';
    },
    _standby: function () {
        this.state = 'standby';
    },
    _disconnected: function () {
        this.state = 'disconnected';
    },
    _isControlling: function () {
        return this.state === 'controlling';
    },
     readBlobAsDataURL: function(blob, callback){
        const a = new FileReader();
        a.onload = function(e) {callback(e.target.result);};
        a.readAsDataURL(blob);
    }
}
