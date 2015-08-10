FieldSet = React.createFactory(require('./impls/field-set.coffee'))

module.exports = (props={})->
        #props.fields = fields
        FieldSet(props)