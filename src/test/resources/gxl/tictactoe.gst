<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<gxl xmlns="http://www.gupro.de/GXL/gxl-1.0.dtd">
    <graph role="graph" edgeids="false" edgemode="directed" id="tictactoe">
        <attr name="$version">
            <string>curly</string>
        </attr>
        <node id="n2">
            <attr name="layout">
                <string>440 202 7 14</string>
            </attr>
        </node>
        <node id="n9">
            <attr name="layout">
                <string>535 110 8 14</string>
            </attr>
        </node>
        <node id="n6">
            <attr name="layout">
                <string>341 205 7 14</string>
            </attr>
        </node>
        <node id="n0">
            <attr name="layout">
                <string>346 293 7 14</string>
            </attr>
        </node>
        <node id="n4">
            <attr name="layout">
                <string>243 377 7 14</string>
            </attr>
        </node>
        <node id="n5">
            <attr name="layout">
                <string>344 379 7 14</string>
            </attr>
        </node>
        <node id="n7">
            <attr name="layout">
                <string>250 206 7 14</string>
            </attr>
        </node>
        <node id="n10">
            <attr name="layout">
                <string>441 293 7 14</string>
            </attr>
        </node>
        <node id="n1">
            <attr name="layout">
                <string>155 102 20 28</string>
            </attr>
        </node>
        <node id="n8">
            <attr name="layout">
                <string>440 378 7 14</string>
            </attr>
        </node>
        <node id="n3">
            <attr name="layout">
                <string>244 293 7 14</string>
            </attr>
        </node>
        <edge from="n2" to="n6">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 443 209 399 184 344 212 12</string>
            </attr>
        </edge>
        <edge from="n2" to="n2">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n2" to="n10">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n2" to="n0">
            <attr name="label">
                <string>diag</string>
            </attr>
            <attr name="layout">
                <string>1081 0 443 209 419 271 349 300 12</string>
            </attr>
        </edge>
        <edge from="n9" to="n9">
            <attr name="label">
                <string>O</string>
            </attr>
        </edge>
        <edge from="n9" to="n1">
            <attr name="label">
                <string>other</string>
            </attr>
            <attr name="layout">
                <string>192 0 539 117 359 86 170 118 12</string>
            </attr>
        </edge>
        <edge from="n6" to="n7">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 344 212 304 188 253 213 12</string>
            </attr>
        </edge>
        <edge from="n6" to="n2">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>499 1 344 212 443 209 11</string>
            </attr>
        </edge>
        <edge from="n6" to="n0">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>338 -1 344 212 349 300 11</string>
            </attr>
        </edge>
        <edge from="n6" to="n6">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n0" to="n5">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n0" to="n4">
            <attr name="label">
                <string>diag</string>
            </attr>
            <attr name="layout">
                <string>750 0 346 305 287 326 246 384 12</string>
            </attr>
        </edge>
        <edge from="n0" to="n10">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>498 2 349 300 444 300 11</string>
            </attr>
        </edge>
        <edge from="n0" to="n6">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>878 0 349 300 378 257 344 212 12</string>
            </attr>
        </edge>
        <edge from="n0" to="n7">
            <attr name="label">
                <string>diag</string>
            </attr>
            <attr name="layout">
                <string>816 4 349 293 320 238 253 213 12</string>
            </attr>
        </edge>
        <edge from="n0" to="n3">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>1121 -4 346 299 305 280 247 300 12</string>
            </attr>
        </edge>
        <edge from="n0" to="n8">
            <attr name="label">
                <string>diag</string>
            </attr>
        </edge>
        <edge from="n0" to="n2">
            <attr name="label">
                <string>diag</string>
            </attr>
        </edge>
        <edge from="n0" to="n0">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n4" to="n4">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n4" to="n3">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 245 377 226 345 247 300 12</string>
            </attr>
        </edge>
        <edge from="n4" to="n0">
            <attr name="label">
                <string>diag</string>
            </attr>
        </edge>
        <edge from="n4" to="n5">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>499 0 246 384 347 386 11</string>
            </attr>
        </edge>
        <edge from="n5" to="n8">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n5" to="n5">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n5" to="n0">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 347 379 329 344 349 300 12</string>
            </attr>
        </edge>
        <edge from="n5" to="n4">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 347 386 300 416 246 384 12</string>
            </attr>
        </edge>
        <edge from="n7" to="n7">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n7" to="n3">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n7" to="n0">
            <attr name="label">
                <string>diag</string>
            </attr>
        </edge>
        <edge from="n7" to="n6">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>499 0 253 213 344 212 11</string>
            </attr>
        </edge>
        <edge from="n10" to="n2">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 444 300 474 253 443 209 12</string>
            </attr>
        </edge>
        <edge from="n10" to="n0">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>663 -8 441 306 399 321 349 300 12</string>
            </attr>
        </edge>
        <edge from="n10" to="n10">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n10" to="n8">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n1" to="n1">
            <attr name="label">
                <string>X</string>
            </attr>
        </edge>
        <edge from="n1" to="n9">
            <attr name="label">
                <string>other</string>
            </attr>
            <attr name="layout">
                <string>137 0 170 118 356 145 539 117 12</string>
            </attr>
        </edge>
        <edge from="n1" to="n1">
            <attr name="label">
                <string>turn</string>
            </attr>
        </edge>
        <edge from="n8" to="n5">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 443 385 400 417 347 386 12</string>
            </attr>
        </edge>
        <edge from="n8" to="n8">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n8" to="n10">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 443 385 475 341 444 300 12</string>
            </attr>
        </edge>
        <edge from="n8" to="n0">
            <attr name="label">
                <string>diag</string>
            </attr>
            <attr name="layout">
                <string>800 -7 440 383 382 356 349 300 12</string>
            </attr>
        </edge>
        <edge from="n3" to="n4">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
        <edge from="n3" to="n3">
            <attr name="label">
                <string>C</string>
            </attr>
        </edge>
        <edge from="n3" to="n7">
            <attr name="label">
                <string>next</string>
            </attr>
            <attr name="layout">
                <string>500 0 247 293 228 257 253 213 12</string>
            </attr>
        </edge>
        <edge from="n3" to="n0">
            <attr name="label">
                <string>next</string>
            </attr>
        </edge>
    </graph>
</gxl>
