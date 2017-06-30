const gulp = require('gulp');
const ts = require('gulp-typescript');
const merge = require('merge2'); 

gulp.task('default', function () {

    const tsProject = ts.createProject('tsconfig.json');

    var tsResult = gulp.src("src/**/*.ts") 
        .pipe(tsProject());

    return merge([
        tsResult.dts.pipe(gulp.dest('lib')),
        tsResult.js.pipe(gulp.dest('lib'))
    ]);
});